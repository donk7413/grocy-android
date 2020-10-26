package xyz.zedler.patrick.grocy;

import android.app.Application;

import com.android.volley.VolleyError;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import xyz.zedler.patrick.grocy.helper.DownloadHelper;
import xyz.zedler.patrick.grocy.model.GroupedListItem;
import xyz.zedler.patrick.grocy.model.Location;
import xyz.zedler.patrick.grocy.model.MissingItem;
import xyz.zedler.patrick.grocy.model.Product;
import xyz.zedler.patrick.grocy.model.ProductGroup;
import xyz.zedler.patrick.grocy.model.QuantityUnit;
import xyz.zedler.patrick.grocy.model.ShoppingList;
import xyz.zedler.patrick.grocy.model.ShoppingListItem;
import xyz.zedler.patrick.grocy.model.StockItem;
import xyz.zedler.patrick.grocy.util.Constants;

public class MyApps extends Application {
    //Synchro
    public static Date lastSynced;


    //Stocks
    public static ArrayList<StockItem> stockItems;
    public static ArrayList<StockItem> expiringItems;
    public static ArrayList<StockItem> expiredItems;
    public static ArrayList<StockItem> missingStockItems;
    public static ArrayList<String> shoppingListProductIds;

    //Common
    public static ArrayList<MissingItem> missingItems;
    public static ArrayList<QuantityUnit> quantityUnits;
    public static ArrayList<Location> locations;
    public static ArrayList<ProductGroup> productGroups;
    public static ArrayList<Product> products;
    public static ArrayList<Integer> missingProductIds;


    //ShoppingList
    public static ArrayList<ShoppingList> shoppingLists;
    public static ArrayList<ShoppingListItem> shoppingListItems;
    public static ArrayList<ShoppingListItem> shoppingListItemsSelected;
    public static ArrayList<ShoppingListItem> missingShoppingListItems;
    public static ArrayList<ShoppingListItem> undoneShoppingListItems;

    public static void needInit()
    {
        if(stockItems == null || stockItems.size() == 0)
        {
            stockItems =  new ArrayList<>();
        }
        if(expiringItems == null || expiringItems.size() == 0)
        {
            expiringItems =  new ArrayList<>();
        }
        if(expiredItems == null || expiredItems.size() == 0)
        {
            expiredItems =  new ArrayList<>();
        }
        if(missingStockItems == null || missingStockItems.size() == 0)
        {
            missingStockItems =  new ArrayList<>();
        }
        if(shoppingListProductIds == null || shoppingListProductIds.size() == 0)
        {
            shoppingListProductIds =  new ArrayList<>();
        }


        if(missingItems == null || missingItems.size() == 0)
        {
            missingItems =  new ArrayList<>();
        }
        if(quantityUnits == null || quantityUnits.size() == 0)
        {
            quantityUnits =  new ArrayList<>();
        }
        if(locations == null || locations.size() == 0)
        {
            locations =  new ArrayList<>();
        }
        if(productGroups == null || productGroups.size() == 0)
        {
            productGroups =  new ArrayList<>();
        }
        if(products == null || products.size() == 0)
        {
            products =  new ArrayList<>();
        }
        if(missingProductIds == null || missingProductIds.size() == 0)
        {
            missingProductIds =  new ArrayList<>();
        }

        if(shoppingLists == null || shoppingLists.size() == 0)
        {
            shoppingLists =  new ArrayList<>();
        }
        if(shoppingListItems == null || shoppingListItems.size() == 0)
        {
            shoppingListItems =  new ArrayList<>();
        }
        if(shoppingListItemsSelected == null || shoppingListItemsSelected.size() == 0)
        {
            shoppingListItemsSelected =  new ArrayList<>();
        }
        if(missingShoppingListItems == null || missingShoppingListItems.size() == 0)
        {
            missingShoppingListItems =  new ArrayList<>();
        }
        if(undoneShoppingListItems == null || undoneShoppingListItems.size() == 0)
        {
            undoneShoppingListItems =  new ArrayList<>();
        }
    }


    public ArrayList<DownloadHelper.QueueItem> getSync(DownloadHelper dlHelper)
    {

        ArrayList<DownloadHelper.QueueItem> queueItems = new ArrayList<DownloadHelper.QueueItem>();

        queueItems.add(dlHelper.getStockItems(stockItems -> MyApps.stockItems = stockItems));
        queueItems.add(dlHelper.getVolatile((expiring, expired, missing) -> {
                    MyApps.expiringItems = expiring;
                    MyApps.expiredItems = expired;
                    MyApps.missingItems = missing;

                }));
        queueItems.add(dlHelper.getShoppingListItems(shoppingListItems -> {
                    MyApps.shoppingListProductIds = new ArrayList<>();
                    for(ShoppingListItem item : shoppingListItems) {
                        if(item.getProductId() != null && !item.getProductId().isEmpty()) {
                            MyApps.shoppingListProductIds.add(item.getProductId());
                        }
                    }
                }));
        queueItems.add(dlHelper.getShoppingListItems(shoppingListItems -> {
                    MyApps.shoppingListProductIds = new ArrayList<>();
                    for(ShoppingListItem item : shoppingListItems) {
                        if(item.getProductId() != null && !item.getProductId().isEmpty()) {
                            MyApps.shoppingListProductIds.add(item.getProductId());
                        }
                    }
                }));
        queueItems.add(dlHelper.getLocations(locations -> {
                    MyApps.locations = locations;

                }));

       /*
        queueItems.add(dlHelper.getShoppingLists(listItems -> MyApps.shoppingLists = listItems));
        queueItems.add(dlHelper.getShoppingListItems(listItems -> MyApps.shoppingListItems = listItems));
        queueItems.add(dlHelper.getProductGroups(listItems -> MyApps.productGroups = listItems));
        queueItems.add(dlHelper.getQuantityUnits(listItems -> MyApps.quantityUnits = listItems));
        queueItems.add(dlHelper.getProducts(listItems -> MyApps.products = listItems));

*/
        return queueItems;


    }




}
