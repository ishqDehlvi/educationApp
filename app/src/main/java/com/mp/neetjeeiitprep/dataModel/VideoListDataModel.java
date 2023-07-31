package com.mp.neetjeeiitprep.dataModel;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


    public class VideoListDataModel {

        @SerializedName("code")
        @Expose
        private String code;
        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("videolist")
        @Expose
        private List<Video> videolist = null;
        @SerializedName("type_name")


        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public List<Video> getVideolist() {
            return videolist;
        }

        public void setVideolist(List<Video> videolist) {
            this.videolist = videolist;
        }

        public class Video {

            @SerializedName("videolist_id")
            @Expose
            private String videolistId;
            @SerializedName("type_id")
            @Expose
            private String typeId;
            @SerializedName("video_title")
            @Expose
            private String videoTitle;
            @SerializedName("video_url")
            @Expose
            private String videoUrl;
            @SerializedName("status")
            @Expose
            private String status;

            public String getVideolistId() {
                return videolistId;
            }

            public void setVideolistId(String videolistId) {
                this.videolistId = videolistId;
            }

            public String getTypeId() {
                return typeId;
            }

            public void setTypeId(String typeId) {
                this.typeId = typeId;
            }

            public String getVideoTitle() {
                return videoTitle;
            }

            public void setVideoTitle(String videoTitle) {
                this.videoTitle = videoTitle;
            }

            public String getVideoUrl() {
                return videoUrl;
            }

            public void setVideoUrl(String videoUrl) {
                this.videoUrl = videoUrl;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

        }

    }