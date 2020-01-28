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
import com.everlapp.androidexamples.utils.RecyclerPaginationListener
import kotlinx.android.synthetic.main.activity_epoxy_example.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import kotlin.math.abs


class EpoxyExampleActivity : AppCompatActivity(), SampleController.AdapterCallbacks {

    private val listData = mutableListOf<Event>()

    private var currentOffset = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_epoxy_example)

        //val recyclerView = findViewById<EpoxyRecyclerView>(R.id.epoxy_recycler_view)

        epoxy_recycler_view.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(this)
        epoxy_recycler_view.layoutManager = layoutManager

        val controller = SampleController(this)
        epoxy_recycler_view.adapter = controller.adapter

        //recyclerView.setControllerAndBuildModels(controller)
        //recyclerView.setController(controller)

        //controller.requestModelBuild()

        //val event = Event(0, "no image", "(0) event", "(0) event description", System.currentTimeMillis())

        //listData.add(event)
        for (i in 0..9) {
            listData.add(
                    Event(i.toLong(), "no image", "($i) event", "($i) event description", System.currentTimeMillis())
            )
        }

        controller.setData(listData, true)
        Timber.e("INIT List data: ${listData.size}")
//        listData.forEach {
//            Timber.e("::: $it")
//        }


        // initDragging(epoxy_recycler_view, controller)

        initSwiping(epoxy_recycler_view, controller)

        // Удалить не больше, чем элементов на экране
        /*GlobalScope.launch(Dispatchers.Main) {
            delay(4 * 1000)
            listData.removeAt(0)
            listData.removeAt(1)
            listData.removeAt(2)
            listData.removeAt(3)
            listData.removeAt(4)
            controller.setData(listData)
        }*/

        // Example infinity Pagination with show progress item
        epoxy_recycler_view.addOnScrollListener(object : RecyclerPaginationListener(layoutManager) {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
            }

            override fun loadMoreItems() {
                currentOffset += 10
                Timber.d("CURRENT Offset: $currentOffset")

                controller.setData(listData, true)
                GlobalScope.launch(Dispatchers.Main) {
                    delay(2000)

                    generateNewItems(currentOffset)
                    controller.setData(listData, false)
                }


                // todo Add progress model
            }

            override fun isLastPage(): Boolean = false

            override fun isLoading(): Boolean = false
        })
    }


    fun generateNewItems(offset: Int) {
        for (i in offset..offset + 9) {
            listData.add(
                    Event(i.toLong(), "no image", "($i) event", "($i) event description", System.currentTimeMillis())
            )
        }
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


    private fun initSwiping(recyclerView: RecyclerView, controller: SampleController) {
        EpoxyTouchHelper.initSwiping(recyclerView)
                .leftAndRight()
                .withTarget(EventModel::class.java)
                .andCallbacks(object : EpoxyTouchHelper.SwipeCallbacks<EventModel>() {

                    override fun clearView(model: EventModel?, itemView: View?) {
                        itemView?.setBackgroundColor(Color.WHITE)
                    }

                    override fun onSwipeCompleted(model: EventModel?, itemView: View?, position: Int, direction: Int) {
                        Timber.e("Swipe completed!!!")
                        listData.removeAt(position)
                        Timber.e("AFTER SWIPE List data: ${listData.size} CUR Pos: $position Direction: $direction")
                        listData.forEach {
                            Timber.e(">>> $it")
                        }
                        controller.setData(listData, true)
                    }

                    override fun isSwipeEnabledForModel(model: EventModel?): Boolean {
                        return super.isSwipeEnabledForModel(model)
                    }

                    override fun onSwipeProgressChanged(model: EventModel?, itemView: View?, swipeProgress: Float, canvas: Canvas?) {
                        val alpha = (abs(swipeProgress) * 255).toInt()
                        if (swipeProgress > 0) {
                          itemView?.setBackgroundColor(Color.argb(alpha, 0, 255, 0))
                        } else {
                          itemView?.setBackgroundColor(Color.argb(alpha, 255, 0, 0))
                        }
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