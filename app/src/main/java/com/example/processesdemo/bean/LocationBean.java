package com.example.processesdemo.bean;

import java.util.Observer;

public class LocationBean {

    /**
     * code : 0
     * data : {"ip":"123.149.237.181","country":"中国","area":"","region":"河南","city":"郑州","county":"XX","isp":"电信","country_id":"CN","area_id":"","region_id":"410000","city_id":"410100","county_id":"xx","isp_id":"100017"}
     */

    private int code;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * ip : 123.149.237.181
         * country : 中国
         * area :
         * region : 河南
         * city : 郑州
         * county : XX
         * isp : 电信
         * country_id : CN
         * area_id :
         * region_id : 410000
         * city_id : 410100
         * county_id : xx
         * isp_id : 100017
         */

        private Object ip;
        private Object country;
        private Object area;
        private Object region;
        private Object city;
        private Object county;
        private Object isp;
        private Object country_id;
        private Object area_id;
        private Object region_id;
        private Object city_id;
        private Object county_id;
        private Object isp_id;

        @Override
        public String toString() {
            return "DataBean{" +
                    "ip='" + ip + '\'' +
                    ", country='" + country + '\'' +
                    ", area='" + area + '\'' +
                    ", region='" + region + '\'' +
                    ", city='" + city + '\'' +
                    ", county='" + county + '\'' +
                    ", isp='" + isp + '\'' +
                    ", country_id='" + country_id + '\'' +
                    ", area_id='" + area_id + '\'' +
                    ", region_id='" + region_id + '\'' +
                    ", city_id='" + city_id + '\'' +
                    ", county_id='" + county_id + '\'' +
                    ", isp_id='" + isp_id + '\'' +
                    '}';
        }

        public Object getIp() {
            return ip;
        }

        public void setIp(Object ip) {
            this.ip = ip;
        }

        public Object getCountry() {
            return country;
        }

        public void setCountry(Object country) {
            this.country = country;
        }

        public Object getArea() {
            return area;
        }

        public void setArea(Object area) {
            this.area = area;
        }

        public Object getRegion() {
            return region;
        }

        public void setRegion(Object region) {
            this.region = region;
        }

        public Object getCity() {
            return city;
        }

        public void setCity(Object city) {
            this.city = city;
        }

        public Object getCounty() {
            return county;
        }

        public void setCounty(Object county) {
            this.county = county;
        }

        public Object getIsp() {
            return isp;
        }

        public void setIsp(Object isp) {
            this.isp = isp;
        }

        public Object getCountry_id() {
            return country_id;
        }

        public void setCountry_id(Object country_id) {
            this.country_id = country_id;
        }

        public Object getArea_id() {
            return area_id;
        }

        public void setArea_id(Object area_id) {
            this.area_id = area_id;
        }

        public Object getRegion_id() {
            return region_id;
        }

        public void setRegion_id(Object region_id) {
            this.region_id = region_id;
        }

        public Object getCity_id() {
            return city_id;
        }

        public void setCity_id(Object city_id) {
            this.city_id = city_id;
        }

        public Object getCounty_id() {
            return county_id;
        }

        public void setCounty_id(Object county_id) {
            this.county_id = county_id;
        }

        public Object getIsp_id() {
            return isp_id;
        }

        public void setIsp_id(Object isp_id) {
            this.isp_id = isp_id;
        }
    }

}
