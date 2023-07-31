package com.mp.neetjeeiitprep.network;

import com.google.gson.JsonElement;
import com.mp.neetjeeiitprep.dataModel.CategoryDataModel;
import com.mp.neetjeeiitprep.dataModel.ContactUsDataModel;
import com.mp.neetjeeiitprep.dataModel.HistoryListDataModel;
import com.mp.neetjeeiitprep.dataModel.LoginRegisterResponseDataModel;
import com.mp.neetjeeiitprep.dataModel.PdfListNcertDataModel;
import com.mp.neetjeeiitprep.dataModel.QuestionListDataModel;
import com.mp.neetjeeiitprep.dataModel.ResultDataModel;
import com.mp.neetjeeiitprep.dataModel.ResultListDataModel;
import com.mp.neetjeeiitprep.dataModel.SubTypeDataModel;
import com.mp.neetjeeiitprep.dataModel.VideoChapterListDataModel;
import com.mp.neetjeeiitprep.dataModel.VideoListDataModel;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface WebApi {



    @FormUrlEncoded
    @POST("user/register")
    Call<LoginRegisterResponseDataModel>registerUser(@FieldMap Map<String, String> fields);

    @FormUrlEncoded
    @POST("user/login")
    Call<LoginRegisterResponseDataModel>loginUser(@FieldMap Map<String, String> fields);

    @FormUrlEncoded
    @POST("user/social_login")
    Call<LoginRegisterResponseDataModel>socialLoginForUser(@FieldMap Map<String, String> fields);

    @FormUrlEncoded
    @POST("user/edit_profile")
    Call<LoginRegisterResponseDataModel>editProfile(@FieldMap Map<String, String> fields);

    @FormUrlEncoded
    @POST("user/change_password")
    Call<JsonElement>changePassword(@FieldMap Map<String, String> fields);

    @FormUrlEncoded
    @POST("user/forgot_password")
    Call<JsonElement>requestForgotPassword(@FieldMap Map<String,String> fields);

    @GET("category/category_list")
    Call<CategoryDataModel>getCategoryList();

    @GET("category/subcategory_list")
    Call<CategoryDataModel>getSubCategoryList(@Query("cid") String catID);

    @GET("category/subcategory_list")
    Call<JsonElement>getPreYearSubCategoryList(@Query("cid") String catID);

    @GET("category/type_list")
    Call<SubTypeDataModel>getSubTypeList(@Query("cat_id")String catId, @Query("subcat_id") String subCat_Id, @Query("type")String type);

    @FormUrlEncoded
    @POST("question/question_mock")
    Call<QuestionListDataModel>getMockTestQuestions(@FieldMap Map<String, String> fields);

    @FormUrlEncoded
    @POST("question/question_demo")
    Call<JsonElement>getStudyAndPreviousQuestions(@FieldMap Map<String, String> fields);

    @FormUrlEncoded
    @POST("question/history_demo")
    Call<QuestionListDataModel>getQuestionListForHistory(@FieldMap Map<String, String> fields);

    @FormUrlEncoded
    @POST("question/history_submit")
    Call<JsonElement>saveMcqProgress(@FieldMap Map<String, String> fields);

    @FormUrlEncoded
    @POST("question/history_list")
    Call<HistoryListDataModel>getHistoryList(@FieldMap Map<String, String> fields);

    @FormUrlEncoded
    @POST("question/result_submit")
    Call<ResultDataModel>getResultData(@FieldMap Map<String, String> fields);

    @FormUrlEncoded
    @POST("question/result_list")
    Call<ResultListDataModel>getResultList(@FieldMap Map<String, String> fields);
    @FormUrlEncoded
    @POST("category/type_list_pdf")
    Call<JsonElement>getPdfList(@FieldMap Map<String, String> fields);

    /*@FormUrlEncoded
    @POST("question/question_list")
    Call<QuestionListDataModel>getMockTestQuestionsForPractice(@FieldMap Map<String, String> fields);*/


    @GET("home/global")
    Call<ContactUsDataModel>getAppsDetails();

    @FormUrlEncoded
    @POST("video/video_typelist")
    Call<VideoChapterListDataModel>getVideoChapters(@Field("sub_id") String subId);

    @FormUrlEncoded
    @POST("video/video_list")
    Call<VideoListDataModel>getVideoList(@Field("type_id") String subId);

    @GET("home/ad_list")
    Call<JsonElement>getAdd();
}
