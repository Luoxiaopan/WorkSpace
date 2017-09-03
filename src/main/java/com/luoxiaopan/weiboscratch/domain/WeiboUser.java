package com.luoxiaopan.weiboscratch.domain;

/**
 * @author luoxiaopan
 * @version 2017/7/1
 */
public class WeiboUser
{
    private String userName;

    private String userId;

    private String time;

    public WeiboUser(String userName, String userId, String time)
    {
        this.userName = userName;
        this.userId = userId;
        this.time = time;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getUserId()
    {
        return userId;
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WeiboUser weiboUser = (WeiboUser) o;

        if (!userName.equals(weiboUser.userName)) return false;
        return userId.equals(weiboUser.userId);
    }

    @Override
    public int hashCode()
    {
        int result = userName.hashCode();
        result = 31 * result + userId.hashCode();
        return result;
    }

    @Override
    public String toString()
    {
        return "WeiboUser{" +
                "userName='" + userName + '\'' +
                ", userId='" + userId + '\'' +
                "}\n";
    }

    public String getTime()
    {
        return time;
    }

    public void setTime(String time)
    {
        this.time = time;
    }
}
