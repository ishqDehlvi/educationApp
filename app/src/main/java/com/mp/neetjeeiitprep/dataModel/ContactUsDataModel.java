package com.mp.neetjeeiitprep.dataModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ContactUsDataModel {

    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("global")
    @Expose
    private Global global;

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

    public Global getGlobal() {
        return global;
    }

    public void setGlobal(Global global) {
        this.global = global;
    }


    public class Global {

        @SerializedName("global_id")
        @Expose
        private String globalId;
        @SerializedName("about_us")
        @Expose
        private String aboutUs;
        @SerializedName("privacy")
        @Expose
        private String privacy;
        @SerializedName("our_team")
        @Expose
        private String ourTeam;
        @SerializedName("reg_address")
        @Expose
        private String regAddress;
        @SerializedName("corporate_address")
        @Expose
        private String corporateAddress;
        @SerializedName("main_branch_address")
        @Expose
        private String mainBranchAddress;
        @SerializedName("wats_phone")
        @Expose
        private String watsPhone;
        @SerializedName("toll_phone")
        @Expose
        private String tollPhone;
        @SerializedName("buis_email")
        @Expose
        private String buisEmail;
        @SerializedName("can_email")
        @Expose
        private String canEmail;
        @SerializedName("fb_link")
        @Expose
        private String fbLink;
        @SerializedName("twt_link")
        @Expose
        private String twtLink;
        @SerializedName("linked_link")
        @Expose
        private String linkedLink;
        @SerializedName("google_link")
        @Expose
        private String googleLink;

        public String getGlobalId() {
            return globalId;
        }

        public void setGlobalId(String globalId) {
            this.globalId = globalId;
        }


        public String getOurTeam() {
            return ourTeam;
        }

        public void setOurTeam(String ourTeam) {
            this.ourTeam = ourTeam;
        }

        public String getRegAddress() {
            return regAddress;
        }

        public void setRegAddress(String regAddress) {
            this.regAddress = regAddress;
        }

        public String getCorporateAddress() {
            return corporateAddress;
        }

        public void setCorporateAddress(String corporateAddress) {
            this.corporateAddress = corporateAddress;
        }

        public String getMainBranchAddress() {
            return mainBranchAddress;
        }

        public void setMainBranchAddress(String mainBranchAddress) {
            this.mainBranchAddress = mainBranchAddress;
        }

        public String getWatsPhone() {
            return watsPhone;
        }

        public void setWatsPhone(String watsPhone) {
            this.watsPhone = watsPhone;
        }

        public String getTollPhone() {
            return tollPhone;
        }

        public void setTollPhone(String tollPhone) {
            this.tollPhone = tollPhone;
        }

        public String getBuisEmail() {
            return buisEmail;
        }

        public void setBuisEmail(String buisEmail) {
            this.buisEmail = buisEmail;
        }

        public String getCanEmail() {
            return canEmail;
        }

        public void setCanEmail(String canEmail) {
            this.canEmail = canEmail;
        }

        public String getFbLink() {
            return fbLink;
        }

        public void setFbLink(String fbLink) {
            this.fbLink = fbLink;
        }

        public String getTwtLink() {
            return twtLink;
        }

        public void setTwtLink(String twtLink) {
            this.twtLink = twtLink;
        }

        public String getLinkedLink() {
            return linkedLink;
        }

        public void setLinkedLink(String linkedLink) {
            this.linkedLink = linkedLink;
        }

        public String getGoogleLink() {
            return googleLink;
        }

        public void setGoogleLink(String googleLink) {
            this.googleLink = googleLink;
        }

        public String getAboutUs() {
            return aboutUs;
        }

        public void setAboutUs(String aboutUs) {
            this.aboutUs = aboutUs;
        }

        public String getPrivacy() {
            return privacy;
        }

        public void setPrivacy(String privacy) {
            this.privacy = privacy;
        }
    }

}
