package com.everlapp.androidexamples.network;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SocketConnection {

    private final OkHttpClient client = new OkHttpClient();

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private OkHttpClient clientBuilder;


    public void exampleRequests(String url) throws IOException {

        RequestBody body = RequestBody.create(JSON, "jsonString");
        Request requestPost = new Request.Builder()
                .url(url)
                .header("Authorization", "ClientID")
                .post(body)
                .build();

        Request requestGet = new Request.Builder()
                .url(url)
                .header("User-Agent", "OkHttp headers.java")
                .addHeader("Accept", "application/json; q=0.5")
                // .addHeader()
                .build();

        // Синхронный запрос
        Response response = client.newCall(requestGet).execute();

        // Асинхронный запрос
        client.newCall(requestGet).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }


    public String getSipleUrl(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        if (response.body() != null) {
            return response.body().string();
        } else return "";
    }





    private void configureOkHttpTimeouts() {
        clientBuilder = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                // .authenticator()
                // .addInterceptor()              // Application interceptor
                // .addNetworkInterceptor()       // Network interceptor
                .build();
    }

    // =============================================================================================
    // Retrofit - configure and Requests

//    public GetMapsRestAdapter() {
//        retrofit = new Retrofit.Builder()
//                .baseUrl(AppConfig.URL_MAP_SITE_API)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        downloadMapApi = retrofit.create(IDownloadMapAPI.class);
//    }


    /**
     * Request for receive track list
     * on backend server by user ID
     *
     * @param headers   - auth header
     * @param apiKey    - as user ID
     * @return
     */
//    @FormUrlEncoded
//    @POST(AppConstants.API_VERSION_PATH + "/getshorttracks")
//    Observable<ResponseGetAllTracksRest> getTracksFromBackendApi(@HeaderMap Map<String, String> headers,
//                                                                 @Field("api_key") String apiKey);


    /**
     * Send tracks from device to
     * backend server
     *
     * @param lat           - latitude
     * @param lon           - longitude
     * @param created       - date create
     * @param apiKey        - as user ID
     * @param trackPublic   - track is public or not
     * @param trackJSON     - track as JSON string
     * @return
     */
//    @FormUrlEncoded
//    @POST(AppConstants.API_VERSION_PATH + "/savetracks")
//    Observable<ResponseResultRest> sendTracks2BackendApi(
//            @HeaderMap Map<String, String> headers,
//            @Field("latitude") String lat,
//            @Field("longitude") String lon,
//            @Field("altitude") String alt,
//            @Field("created") String created,
//            @Field("api_key") String apiKey,
//            @Field("is_public") String trackPublic,
//            @Field("json_track") String trackJSON,
//            @Field("json_userpoints") String userPointsJSON);


    /**
     * Download JSON tracks from backend server
     *
     * @param date
     * @return
     */
//    @GET(AppConstants.API_VERSION_PATH + "/downloadtrack/{created}")
//    Observable<ResponseGetOneTrackRest>
//    downloadTracksFromBackendApi(@HeaderMap Map<String, String> headers,
//                                 @Path("created") String date);


    /**
     * Request on register user via email
     * @param name      - user name
     * @param email     - user email
     * @param password  - user password
     * @return
     */
//    @FormUrlEncoded
//    @POST(AppConstants.API_VERSION_PATH + "/register")
//    Observable<ResponseResultRest> setMailUserFromApi(@Field("first_name") String name,
//                                                      @Field("email") String email,
//                                                      @Field("password") String password);


    /**
     * Request to receive user data via email data
     * @param email     - user email
     * @param password  - user password
     * @return
     */
//    @FormUrlEncoded
//    @POST(AppConstants.API_VERSION_PATH + "/maillogin")
//    Observable<UserRestModel> getMailUserFromApi(@Field("email") String email,
//                                                 @Field("password") String password);



    /**
     * Request on receive user data via
     * social networks data
     * @param authType          - type authorization (1-email, 2-google, 3-facebook)
     * @param userId
     * @param accessToken
     * @param firstName
     * @param lastName
     * @param email
     * @param gender
     * @param age
     * @param country
     * @param photoUrl
     * @return
     */
//    @FormUrlEncoded
//    @POST(AppConstants.API_VERSION_PATH + "/socialloginregister")
//    Observable<UserRestModel> getSocialUserFromApi(@Field("auth_type") int authType,
//                                                   @Field("user_id") String userId,
//                                                   @Field("access_token") String accessToken,
//                                                   @Field("first_name") String firstName,
//                                                   @Field("last_name") String lastName,
//                                                   @Field("email") String email,
//                                                   @Field("gender") String gender,
//                                                   @Field("age") String age,
//                                                   @Field("country") String country,
//                                                   @Field("photo_url") String photoUrl);

    /**
     * Record to Db on backend server user data
     * @param headers
     * @param authType
     * @param apiKey
     * @param firstName
     * @param lastName
     * @param gender
     * @param age
     * @param growth
     * @param weight
     * @param country
     * @param photoUrl
     * @return
     */
//    @FormUrlEncoded
//    @POST(AppConstants.API_VERSION_PATH + "/setuserprofile")
//    Observable<ResponseResultRest> setUserProfileFromApi(@HeaderMap Map<String, String> headers,
//                                                         @Field("auth_type") int authType,
//                                                         @Field("api_key") String apiKey,
//
//                                                         @Field("first_name") String firstName,
//                                                         @Field("last_name") String lastName,
//                                                         //@Field("email") String email,
//                                                         @Field("gender") int gender,
//                                                         @Field("age") int age,
//                                                         @Field("growth") int growth,
//                                                         @Field("weight") int weight,
//                                                         @Field("country") String country,
//                                                         @Field("photo_url") String photoUrl);


    /**
     * Upload user avatar
     * @param file - file for upload
     * @return
     */
//    @Multipart
//    @POST(AppConstants.API_VERSION_PATH + "/avatarupload")
//    Observable<ResponseResultRest> uploadAvatarFromApi(@HeaderMap Map<String, String> headers,
//                                                       @Part MultipartBody.Part file);


    /**
     * Upload user avatar to backend server
     * @param file
     * @param fileName
     * @return
     */
//    public Observable<ResponseResultRest> uploadAvatarToBackend(Map<String, String> headers,
//                                                                File file,
//                                                                String fileName) {
//        RequestBody requestFile =
//                RequestBody.create(MediaType.parse("multipart/form-data"), file);
//
//        MultipartBody.Part body =
//                MultipartBody.Part.createFormData("image", fileName + ".png", requestFile);
//
//        return usersRestApi.uploadAvatarFromApi(headers, body);
//    }


    // =============================================================================================

    /**
     * Socket example
     *
     * 1) Создание сокета
     * 2) Получение от него InputStream
     * 3) Чтение блоками
     * 4) Закрытие сокета
     * 5) Все в блоке try
     *
     */
    public void doInBackground() {
        StringBuilder response = new StringBuilder();
        Socket socket = null;
        try {
            socket = new Socket("https://google.com", 80);

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(1024);
            byte[] buffer = new byte[1024];

            int byteRead;
            InputStream inputStream = socket.getInputStream();

            while ((byteRead = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, byteRead);
                response.append(byteArrayOutputStream.toString("UTF-8"));
            }

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * GET - HttpUrlConnection GET
     */
    public void httpUrlConnectionExampleGET() {
        HttpURLConnection connection = null;
        try {
            URL url = new URL("http://google.com");

            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            InputStream inputStream = new BufferedInputStream(connection.getInputStream());
            // readStream(inputStream)

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }


    /**
     * GET - HttpUrlConnection POST
     */
    public void httpUrlConnectionExamplePOST() {
        HttpURLConnection connection = null;
        try {
            URL url = new URL("http://google.com");

            connection = (HttpURLConnection) url.openConnection();
            connection.addRequestProperty("User-Agent", "My application 1.0");
            connection.addRequestProperty("Content-Type", "application/json; charset=utf-8");
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setChunkedStreamingMode(0);

            OutputStream outputStream = new BufferedOutputStream(connection.getOutputStream());
            // writeStream(outputStream)

            InputStream inputStream = new BufferedInputStream(connection.getInputStream());
            // readStream(inputStream)

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }



}
