package org.example;

import org.example.config.Configuration;
import org.example.core.TicketPool;
import org.example.logging.Logger;
import org.example.threads.Customer;
import org.example.threads.Vendor;
import org.example.ui.CommandLineInterface;

public class Main
{
    public static void main(String[] args)
    {
        Configuration config = CommandLineInterface.configureSystem();
        TicketPool ticketPool = new TicketPool();

        Thread vendor = new Thread(
                new Vendor(ticketPool,
                        config.getTicketReleaseRate()));

        Thread customer = new Thread(new Customer(ticketPool));

        vendor.start();
        customer.start();

        try
        {
            vendor.join();
            customer.join();

        } catch (InterruptedException e)
        {
            Logger.log("Main thread interrupted.");
        }
        Logger.log("System terminated.");
    }
}