package org.seckill.dto;

public class Exposer {

    private  boolean pxposed;

    private  String md5;

    private  int seckillId;

    private long now;

    private long start;

    private  long end;

    @Override
    public String toString() {
        return "Exposer{" +
                "pxposed=" + pxposed +
                ", md5='" + md5 + '\'' +
                ", seckillId=" + seckillId +
                ", now=" + now +
                ", start=" + start +
                ", end=" + end +
                '}';
    }

    public Exposer(boolean pxposed, String md5, int seckillId) {
        this.pxposed = pxposed;
        this.md5 = md5;
        this.seckillId = seckillId;
    }

    public Exposer(boolean pxposed, int seckillId, long now, long start, long end) {
        this.pxposed = pxposed;
        this.seckillId = seckillId;
        this.now = now;
        this.start = start;
        this.end = end;
    }

    public Exposer(boolean pxposed, String md5, int seckillId, long now, long start, long end) {
        this.pxposed = pxposed;
        this.md5 = md5;
        this.seckillId = seckillId;
        this.now = now;
        this.start = start;
        this.end = end;
    }

    public Exposer(boolean pxposed, int seckillId) {
        this.pxposed = pxposed;
        this.seckillId = seckillId;
    }

    public boolean isPxposed() {
        return pxposed;
    }

    public void setPxposed(boolean pxposed) {
        this.pxposed = pxposed;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public int getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(int seckillId) {
        this.seckillId = seckillId;
    }

    public long getNow() {
        return now;
    }

    public void setNow(long now) {
        this.now = now;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }
}
