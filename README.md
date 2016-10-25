# ClamCard kata

Implement a system for a contactless travel card for the London underground.

* The card does not need to be topped up.
* The card charges the owner's bank account directly when used.
* The card is used by touching in and out at train stations.
* The train system accepting this card has two categorical zones of stations, Zone A and Zone B.
* The stations within the zones are as follows:
        -- Zone A
          Asterisk
          Antelope
          Aldgate
          Angel
        -- Zone B
          Bison
          Bugel
          Balham
          Bullhead
          Barbican
        
* Travelling within zone B is more expensive than travelling in Zone A.
* The price of zone B is inclusive of travelling within zone A.
* The fares are as described below:
                        Single  Day     Week    Month
                ZoneA   £2.50   £7.00   £40.00  £145.00
                ZoneB   £3.00   £8.00   £47.00  £165.50
        -- A Single is a journey from one station to another 
        -- A Day fare includes all single journies made within a single day.
        -- A Week fare includes all single journies made within a single week.
        -- A month fare includes all single journies made within a single month.

* If one of the stations is within Zone 2 at any point in a journey, the price for zone 2 will be charged.
* No matter how many journies are made within one of the time boundaries within a particular zone, the price will cap at that time period's fare.
