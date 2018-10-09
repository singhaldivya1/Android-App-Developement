# Project Title
TREASURY INQUIRY APP

## Overview

This project has two Android apps that help users find out how much money the federal
goverment of the US had on hand on any given working day in the year 2017. The data is stored in a text file called treasury-io.txt.
Each line in the file has data for a given working day. Each line contains the following items, separated by
commas, for a given day:
1. Year (either 2017 or 2018)
2. Month (1—12)
3. Day (1—31)
4. Day of week (e.g., “Monday”)
5. Amount held by US at day open in millions of US dollars
6. Amount held by US at day close in millions of US dollars
This project has an SQLite database that formats the data in the text file in a single table. It also define a bound service called BalanceService exposing an API for creating and querying the database.
The first app acts as a client. The main activity in the this app defines fields for creating and querying a database. Upon pressing a first button the activity will issue an API call for creating a database to the service. If the database is created successfully, a user can press a second button to view the amount of money held by the US in each day of a prespecified range.

In addition the UI of the app contains three fields for specifying a date range (e.g., the month and day for the starting day of the date range, and the total number of working days in the range).Upon pressing the second button, the main activity checks that the specified date range is valid. If successful, the activity will issue an API call for obtaining the specified data from the service. The
data will be displayed in a list view contained in a second activity. The user can return to the main activity by pressing the “back” button on the device.

The second app defines a bound service called BalanceService that manages the database as well as database queries. The API exposed by BalanceService consists of the following two remote methods:
1. createDatabase() : Boolean — This zero-argument method reads the text file and creates the SQLite
database used for subsequent querying. It returns true or false depending on whether the database was
successfully created or not.
2. dailyCash(int, int, int, int) : DailyCash[] — This method takes as input 4 integers: a day, a month,
a year, and a number of working days. The first 3 integers denote a date in years 2017 or 2018.
(See extra credit below.) The last integer denotes a number of working days between 1 and 30.

If successful, this method returns an array of DailyCash instances containing the requested data.
Otherwise the method returns a length-zero array. If the database had not been created before this
method is called, the method returns a zero-length array.
In addition to the bound service the second app defines a main activity whose only goal is to display text
explaining the purpose of the app.
