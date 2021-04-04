# My Personal Project

## A POS system with inventory management

**What will the application do?**
- Create and store items which can be modified
- Modify said items in their quantity
- Have a record of items going in and out
- View all the items
- Have the ability to "sell" the items
- Have transaction records

The main use of this application will be for independent vendors who cannot afford
solutions from the large companies. Because of this, it will have a feature set to
match its user base. It won't be connected to the internet because it is not necessary,
 it won't have multi user support because it won't be necessary. It will be for one person
running a small store, that wants to keep track of their sales and make it easier for
them to run their business.

**Who will use it?**
- Small shops
- Street Vendors (Probably would require further development into an android app)
- Basically any place that only has a single user and wants to keep track of sales

**Why is this project of interest to you**
- I noticed that a lot of people here were not using digital systems for item sales and instead still used calculators
and handwritten receipts. So I thought of making a free POS system that was optimized for this market.

**User Story**
- As a user I wish to be able to create a shop
- As a user I wish to be able to add items with names and prices to my shop
- As a user I wish to be able to choose whether to have an inventory system
- As a user I wish to be able to add items in the inventory system
- As a user I wish to be able to add items to a cart
- As a user I wish to be limited by my inventory system so that I don't sell more items than I actually have
- As a user I wish to be able to make a sale with all the items in the cart
- As a user I wish to be able to keep track of my sales
- As a user I wish to be able to save my shop so that I can record my sales and inventory for the future
- As a user I wish to be able to load from saved shops so that I may continue to sell items

**HOW TO USE**

Since this is designed to be a POS system that is used by sales persons, one has to add a catalogue of items
first before being able to purchase items. The system gets the price of the items from this catalogue and thus
creating the items in the catalogue is the first thing that should be done. Additionally, if the user has chosen
the inventory shop, that means that the program will be keeping track of the inventory of the shop and thus after
creating the catalogue, the inventory needs to be added in order to be able to purchase items.

**Phase 4: Task 2**

USE MAP

The implementation of the Map interface was used inside the Shop abstract class. The variable "cart" was declared to be
of apparent type Map<String, InventoryStock> and actual type HashMap<String, InventoryStock>. In this implementation the 
use of map was a necessity in order to fulfill the requirements of the features. The main feature was that the same
item would be grouped together into one single item in the cart. The type InventoryStock had quantity as a private 
but accessible field so when adding multiple units of a single item to cart this would be fine. But adding the same 
item multiple times would create multiple instances of the InventoryStock item that was supposed to represent the same
item, thus it was necessary to know whether the InventoryStock was already inside the cart or not. For this it was
significantly easier to implement a map with the name of the items as the key instead of overriding the equals method
and using a for loop or other such methods. Because the item name of the InventoryStock came from the item name of
the NIStock inside the catalogue Map it would be the same for all of the different addition operations of the single 
item and thus it could be used to determine whether the item already existed in cart. This was then used as a check to 
determine whether to add the item to the cart map or whether to edit the already existing item in the cart map. This 
did not work on HashSet even with an overrided equals method for InventoryStock thus it was determined that Map would 
function the best for this purpose. 