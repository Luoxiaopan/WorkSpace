package com.luoxiaopan.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 生产者与消费者 最简单的代码
 * @author luoxiaopan
 * @version 2017/4/4
 */
public class Restaurant
{
    WaitPerson waitPerson = new WaitPerson(this);

    Chef chef = new Chef(this);

    Meal meal;

    ExecutorService executorService = Executors.newCachedThreadPool();

    public Restaurant()
    {
        executorService.execute(waitPerson);
        executorService.execute(chef);
    }

    public static void main(String[] args)
    {
        new Restaurant();
    }
}

class Meal
{
    private final int OrderNum;

    Meal(int orderNum)
    {
        OrderNum = orderNum;
    }

    @Override
    public String toString()
    {
        return "Meal{" +
                "OrderNum=" + OrderNum +
                '}';
    }
}

/**
 * 消费者
 */
class WaitPerson implements Runnable
{
    private Restaurant restaurant;

    WaitPerson(Restaurant restaurant)
    {
        this.restaurant = restaurant;
    }

    public void run()
    {
        try
        {
            while (!Thread.interrupted())
            {
                synchronized(this)
                {
                    while (restaurant.meal == null)
                    {
                        wait();
                    }
                }
                System.out.println("WaitPerson get" + restaurant.meal);
                synchronized (restaurant.chef)
                {
                    restaurant.meal = null;
                    restaurant.chef.notifyAll();
                }
            }

        }
        catch (InterruptedException e)
        {
            System.out.println("WaitPerson Interrupted");
        }
    }
}

/**
 * 生产者
 */
class Chef implements Runnable
{
    private Restaurant restaurant;

    private int count = 0;

    Chef(Restaurant restaurant)
    {
        this.restaurant = restaurant;
    }

    public void run()
    {
        try
        {
            synchronized(this)
            {
                while (!Thread.interrupted())
                {
                    while (restaurant.meal != null)
                    {
                        wait();
                    }
                    if (++count == 10)
                    {
                        System.out.println("Out of food, close.");
                        restaurant.executorService.shutdownNow();
                    }
                    System.out.print("Order up! ");
                    synchronized(restaurant.waitPerson)
                    {
                        restaurant.meal = new Meal(count);
                        restaurant.waitPerson.notifyAll();
                    }
                    TimeUnit.MILLISECONDS.sleep(100);
                }
            }
        }
        catch (InterruptedException e)
        {
            System.out.println("Chef Interrupted");
        }
    }
}