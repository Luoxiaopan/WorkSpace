package com.luoxiaopan.bronzeGlory;

/**
 * @author luoxiaopan
 * @version 2017/9/3
 */
public class Pos
{
    private int x;
    private int y;

    public Pos()
    {
    }

    public Pos(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public int getX()
    {
        return x;
    }

    public void setX(int x)
    {
        this.x = x;
    }

    public int getY()
    {
        return y;
    }

    public void setY(int y)
    {
        this.y = y;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pos pos = (Pos) o;

        if (x != pos.x) return false;
        return y == pos.y;
    }

    @Override
    public int hashCode()
    {
        int result = x;
        result = 31 * result + y;
        return result;
    }

    @Override
    public String toString()
    {
        return "Pos{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
