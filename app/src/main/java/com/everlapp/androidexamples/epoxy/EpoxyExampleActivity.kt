package com.everlapp.androidexamples.epoxy

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.epoxy.EpoxyController
import com.airbnb.epoxy.EpoxyTouchHelper
import com.everlapp.androidexamples.R
import com.everlapp.androidexamples.epoxy.models.Event
import com.everlapp.androidexamples.epoxy.models.EventModel
import kotlinx.android.synthetic.main.activity_epoxy_example.*
import timber.log.Timber

class EpoxyExampleActivity : AppCompatActivity(), SampleController.AdapterCallbacks {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_epoxy_example)

        //val recyclerView = findViewById<EpoxyRecyclerView>(R.id.epoxy_recycler_view)

        epoxy_recycler_view.setHasFixedSize(true)
        epoxy_recycler_view.layoutManager = LinearLayoutManager(this)

        val controller = SampleController(this)
        epoxy_recycler_view.adapter = controller.adapter

        //recyclerView.setControllerAndBuildModels(controller)
        //recyclerView.setController(controller)

        //controller.requestModelBuild()

        val event = Event(0, "no image", "First event", "First event description", System.currentTimeMillis())

        controller.setData(mutableListOf(event,
                event.copy(
                id = 1L,
                title = "Second event",
                description = "Second event description"),
                event.copy(
                        id = 2L,
                        title = "Third event",
                        description = "Third event description"
                ),
                event.copy(
                        id = 3L,
                        title = "Fourth event",
                        description = "Fourth event description"
                )))

        initDragging(epoxy_recycler_view, controller)

        initSwiping(epoxy_recycler_view, controller)
    }


    private fun initDragging(recyclerView: RecyclerView, controller: EpoxyController) {
        EpoxyTouchHelper.initDragging(controller)
                .withRecyclerView(recyclerView)
                .forVerticalList()
                .withTarget(EventModel::class.java)
                .andCallbacks(object: EpoxyTouchHelper.DragCallbacks<EventModel>(){

                    val selectedBackgroundColor = Color.argb(200, 200, 200, 200)
                    var backgroundAnimator: ValueAnimator? = null

                    override fun onModelMoved(fromPosition: Int, toPosition: Int, modelBeingMoved: EventModel?, itemView: View?) {
                        Timber.d("Model moved")
                        // Called when a view has been dragged to a new position.
                        // Epoxy will automatically update the models in the controller and notify
                        // the RecyclerView of the move

                        // You MUST use this callback to update your data to reflect the move
                    }

                    // You may optionally implement the below methods as hooks into the drag lifecycle.
                    // This allows you to style or animate the view as it is dragged.


                    override fun onDragStarted(model: EventModel?, itemView: View?, adapterPosition: Int) {
                        //super.onDragStarted(model, itemView, adapterPosition)
                        Timber.d("onDrag started:")
                        backgroundAnimator = ValueAnimator.ofObject(ArgbEvaluator(), Color.WHITE, selectedBackgroundColor)

                        backgroundAnimator?.addUpdateListener { animation ->
                            itemView?.setBackgroundColor(animation.animatedValue as Int)
                        }

                        backgroundAnimator?.start()

                        itemView?.animate()?.scaleX(1.05f)?.scaleY(1.05f)
                    }


                    override fun onDragReleased(model: EventModel?, itemView: View?) {
                        Timber.d("Drag release")
                        backgroundAnimator?.cancel()

                        backgroundAnimator = ValueAnimator.ofObject(ArgbEvaluator(),
                                ((itemView?.background) as ColorDrawable).color, Color.WHITE  )

                        backgroundAnimator?.addUpdateListener { animation ->
                            itemView.setBackgroundColor(animation.animatedValue as Int)
                        }

                        backgroundAnimator?.start()

                        itemView.animate()?.scaleX(1f)?.scaleY(1f)
                    }

                    override fun clearView(model: EventModel?, itemView: View?) {
                        onDragReleased(model, itemView)
                    }

                    override fun isDragEnabledForModel(model: EventModel?): Boolean {
                        // Override this to toggle disabling dragging for a model
                        return true
                    }
                })
    }


    private fun initSwiping(recyclerView: RecyclerView, controller: EpoxyController) {
        EpoxyTouchHelper.initSwiping(recyclerView)
                .leftAndRight()
                .withTarget(EventModel::class.java)
                .andCallbacks(object : EpoxyTouchHelper.SwipeCallbacks<EventModel>() {
                    override fun clearView(model: EventModel?, itemView: View?) {
                        super.clearView(model, itemView)
                    }

                    override fun onSwipeCompleted(model: EventModel?, itemView: View?, position: Int, direction: Int) {
                        Timber.e("Swipe completed!!!")
                    }

                    override fun isSwipeEnabledForModel(model: EventModel?): Boolean {
                        return super.isSwipeEnabledForModel(model)
                    }

                    override fun onSwipeProgressChanged(model: EventModel?, itemView: View?, swipeProgress: Float, canvas: Canvas?) {
                        super.onSwipeProgressChanged(model, itemView, swipeProgress, canvas)
                    }

                    override fun onSwipeReleased(model: EventModel?, itemView: View?) {
                        super.onSwipeReleased(model, itemView)
                    }

                    override fun onSwipeStarted(model: EventModel?, itemView: View?, adapterPosition: Int) {
                        super.onSwipeStarted(model, itemView, adapterPosition)
                    }
                })

    }



    override fun onEventClickListener(event: Event) {
        Toast.makeText(this, "Event click ID: ${event.id}: Name: ${event.title}", Toast.LENGTH_LONG).show()
    }

}