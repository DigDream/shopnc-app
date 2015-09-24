package com.daxueoo.shopnc.model;

import java.io.Serializable;

/**
 * Created by guodont on 15/9/11.
 */
public class GoodsMessage extends BaseMessage implements Serializable {

    String goods_id;                //商品编号
    String goods_name;              //商品名称
    String goods_price;             //商品价格
    String goods_marketprice;       //商品市场价
    String goods_salenum;           //销量
    String evaluation_good_star;    //评价星级
    String is_appoint;              //评价数
    String group_flag;              //是否抢购
    String xianshi_flag;            //是否限时折扣
    String goods_image;             //图片名称
    String goods_image_url;         //图片地址
    String is_fcode;                //是否为F码商品 1-是 0-否
    String is_presell;              //是否是预售商品 1-是 0-否
    String have_gift;               //是否拥有赠品 1-是 0-否

    public GoodsMessage() {

    }

    public GoodsMessage(String is_fcode, String is_presell, String have_gift, String goods_name, String goods_price, String goods_marketprice, String goods_salenum, String evaluation_good_star, String group_flag, String xianshi_flag, String goods_image, String goods_id) {
        this.is_fcode = is_fcode;
        this.is_presell = is_presell;
        this.have_gift = have_gift;
        this.goods_name = goods_name;
        this.goods_price = goods_price;
        this.goods_marketprice = goods_marketprice;
        this.goods_salenum = goods_salenum;
        this.evaluation_good_star = evaluation_good_star;
        this.group_flag = group_flag;
        this.xianshi_flag = xianshi_flag;
        this.goods_image = goods_image;
        this.goods_id = goods_id;
    }

    @Override
    public String toString() {
        return "GoodsMessage{" +
                "goods_id='" + goods_id + '\'' +
                ", goods_name='" + goods_name + '\'' +
                ", goods_price='" + goods_price + '\'' +
                ", goods_marketprice='" + goods_marketprice + '\'' +
                ", goods_salenum='" + goods_salenum + '\'' +
                ", evaluation_good_star='" + evaluation_good_star + '\'' +
                ", group_flag='" + group_flag + '\'' +
                ", xianshi_flag='" + xianshi_flag + '\'' +
                ", goods_image='" + goods_image + '\'' +
                ", goods_image_url='" + goods_image_url + '\'' +
                ", is_fcode='" + is_fcode + '\'' +
                ", is_appoint='" + is_appoint + '\'' +
                ", is_presell='" + is_presell + '\'' +
                ", have_gift='" + have_gift + '\'' +
                '}';
    }

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getGoods_price() {
        return goods_price;
    }

    public void setGoods_price(String goods_price) {
        this.goods_price = goods_price;
    }

    public String getGoods_marketprice() {
        return goods_marketprice;
    }

    public void setGoods_marketprice(String goods_marketprice) {
        this.goods_marketprice = goods_marketprice;
    }

    public String getGoods_salenum() {
        return goods_salenum;
    }

    public void setGoods_salenum(String goods_salenum) {
        this.goods_salenum = goods_salenum;
    }

    public String getEvaluation_good_star() {
        return evaluation_good_star;
    }

    public void setEvaluation_good_star(String evaluation_good_star) {
        this.evaluation_good_star = evaluation_good_star;
    }

    public String getGroup_flag() {
        return group_flag;
    }

    public void setGroup_flag(String group_flag) {
        this.group_flag = group_flag;
    }

    public String getXianshi_flag() {
        return xianshi_flag;
    }

    public void setXianshi_flag(String xianshi_flag) {
        this.xianshi_flag = xianshi_flag;
    }

    public String getGoods_image() {
        return goods_image;
    }

    public void setGoods_image(String goods_image) {
        this.goods_image = goods_image;
    }

    public String getGoods_image_url() {
        return goods_image_url;
    }

    public void setGoods_image_url(String goods_image_url) {
        this.goods_image_url = goods_image_url;
    }

    public String getIs_fcode() {
        return is_fcode;
    }

    public void setIs_fcode(String is_fcode) {
        this.is_fcode = is_fcode;
    }

    public String getIs_appoint() {
        return is_appoint;
    }

    public void setIs_appoint(String is_appoint) {
        this.is_appoint = is_appoint;
    }

    public String getIs_presell() {
        return is_presell;
    }

    public void setIs_presell(String is_presell) {
        this.is_presell = is_presell;
    }

    public String getHave_gift() {
        return have_gift;
    }

    public void setHave_gift(String have_gift) {
        this.have_gift = have_gift;
    }
}
