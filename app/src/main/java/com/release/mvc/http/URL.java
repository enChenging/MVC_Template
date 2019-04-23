package com.release.mvc.http;

/**
 * @author Mr.release
 * @create 2019/3/22
 * @Describe
 */
public interface URL {

    String BASE_URL="http://api.tianapi.com/";
    String BASE_URL2="";

    String news = BASE_URL + "it?key=4a0090627cf07a50def18da875165740&num=20";
    String tea = BASE_URL2 + "http://sns.maimaicha.com/api?apikey=b4f4ee31a8b9acc866ef2afb754c33e6&format=json&method=news.getSlideshow";

}
