package com.example.pekon.helloandroid.entity;

import java.util.List;

/**
 * create by dsw on 2020-12-05
 **/
public class Categories {
    private boolean error;
    private List<Results> results;

    public class Results {
        private String _id;
        private String en_name;
        private String name;
        private int rank;

        @Override
        public String toString() {
            return "Results{" +
                    "_id='" + _id + '\'' +
                    ", en_name='" + en_name + '\'' +
                    ", name='" + name + '\'' +
                    ", rank=" + rank +
                    '}';
        }
    }
    @Override
    public String toString() {
        return "Categories{" +
                "error=" + error +
                ", results=" + results +
                '}';
    }
}
