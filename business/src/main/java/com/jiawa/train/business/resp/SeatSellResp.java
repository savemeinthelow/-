package com.jiawa.train.business.resp;

public class SeatSellResp {
    private Integer carriageIndex;
    private String row;
    private String col;
    private String seatType;
    private String sell;

    public Integer getCarriageIndex() {
        return carriageIndex;
    }

    public void setCarriageIndex(Integer carriageIndex) {
        this.carriageIndex = carriageIndex;
    }

    public String getRow() {
        return row;
    }

    public void setRow(String row) {
        this.row = row;
    }

    public SeatSellResp(Integer carriageIndex, String row, String col, String seatType, String sell) {
        this.carriageIndex = carriageIndex;
        this.row = row;
        this.col = col;
        this.seatType = seatType;
        this.sell = sell;
    }

    public String getcol() {
        return col;
    }

    public void setcol(String col) {
        this.col = col;
    }

    public String getSeatType() {
        return seatType;
    }

    public void setSeatType(String seatType) {
        this.seatType = seatType;
    }

    public String getSell() {
        return sell;
    }

    public void setSell(String sell) {
        this.sell = sell;
    }
}
