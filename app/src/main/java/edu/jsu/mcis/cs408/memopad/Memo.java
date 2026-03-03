package edu.jsu.mcis.cs408.memopad;

public class Memo {

    private int id;
    private String memo;

    public Memo(int id, String memo) {
        this.id = id;
        this.memo = memo;
    }

    public Memo(String memo) {
        this.memo = memo;
    }

    public int getId() {
        return id;
    }

    public String getMemo() {
        return memo;
    }

    @Override
    public String toString() {
        return "#" + id + ": " + memo;
    }
}