package com.mp.neetjeeiitprep.dataModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginRegisterResponseDataModel {

    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private LoginRegisterData data;

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

    public LoginRegisterData getData() {
        return data;
    }

    public void setData(LoginRegisterData data) {
        this.data = data;
    }

    public class LoginRegisterData {

        @SerializedName("user_id")
        @Expose
        private String userId;
        @SerializedName("reg_date")
        @Expose
        private String regDate;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("address")
        @Expose
        private String address;
        @SerializedName("password")
        @Expose
        private String password;
        @SerializedName("phone")
        @Expose
        private String phone;
        @SerializedName("fb_id")
        @Expose
        private String fbId;
        @SerializedName("gmail_id")
        @Expose
        private Object gmailId;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("device_type")
        @Expose
        private String deviceType;
        @SerializedName("login_using")
        @Expose
        private String loginUsing;
        @SerializedName("firebase_token")
        @Expose
        private String firebaseToken;
        @SerializedName("device_unique_id")
        @Expose
        private String deviceUniqueId;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getRegDate() {
            return regDate;
        }

        public void setRegDate(String regDate) {
            this.regDate = regDate;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getFbId() {
            return fbId;
        }

        public void setFbId(String fbId) {
            this.fbId = fbId;
        }

        public Object getGmailId() {
            return gmailId;
        }

        public void setGmailId(Object gmailId) {
            this.gmailId = gmailId;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getDeviceType() {
            return deviceType;
        }

        public void setDeviceType(String deviceType) {
            this.deviceType = deviceType;
        }

        public String getLoginUsing() {
            return loginUsing;
        }

        public void setLoginUsing(String loginUsing) {
            this.loginUsing = loginUsing;
        }

        public String getFirebaseToken() {
            return firebaseToken;
        }

        public void setFirebaseToken(String firebaseToken) {
            this.firebaseToken = firebaseToken;
        }

        public String getDeviceUniqueId() {
            return deviceUniqueId;
        }

        public void setDeviceUniqueId(String deviceUniqueId) {
            this.deviceUniqueId = deviceUniqueId;
        }

        @Override
        public String toString() {
            return "LoginRegisterData{" +
                    "userId='" + userId + '\'' +
                    ", regDate='" + regDate + '\'' +
                    ", name='" + name + '\'' +
                    ", email='" + email + '\'' +
                    ", address='" + address + '\'' +
                    ", password='" + password + '\'' +
                    ", phone='" + phone + '\'' +
                    ", fbId=" + fbId +
                    ", gmailId=" + gmailId +
                    ", status='" + status + '\'' +
                    ", deviceType='" + deviceType + '\'' +
                    ", loginUsing='" + loginUsing + '\'' +
                    ", firebaseToken='" + firebaseToken + '\'' +
                    ", deviceUniqueId='" + deviceUniqueId + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "LoginRegisterResponseDataModel{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}