package ui.cards;

import exceptions.ItemAlreadyExists;
import exceptions.ItemNotFound;
import exceptions.NotEnoughInventory;
import exceptions.NotPositiveInteger;
import model.shop.NonInventoryShop;
import model.shop.Shop;
import model.stock.InventoryStock;
import model.stock.NIStock;
import ui.Frame;
import ui.Refreshable;
import ui.dialogue.NonInventoryStockEntry;
import ui.dialogue.StringEntry;
import ui.elements.Button;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CatalogueCard extends GeneralCard implements ActionListener {

    private NIStock stock;
    private Shop shop;
    private TextField textField;
    private Refreshable refreshable;

    public CatalogueCard(Frame frame, Refreshable refreshable, NIStock stock, Shop shop) {
        super(frame);
        this.refreshable = refreshable;
        this.init(getLabelNames(stock), getComponentList(stock));
        this.stock = stock;
        this.shop = shop;
    }

    private java.util.List<String> getLabelNames(NIStock stock) {
        java.util.List<String> labelNames = new ArrayList<>();

        labelNames.add(stock.getName());
        String price;
        String stringPrice = String.valueOf(stock.getPrice());
        if (stringPrice.length() >= 2) {
            int decimalPoint1 = stringPrice.length() - 2;
            price = "$" + stringPrice.substring(0, decimalPoint1) + "." + stringPrice.substring(decimalPoint1);
        } else {
            price = stringPrice;
            for (int i = 0; i < 2 - stringPrice.length(); i++) {
                price = "0" + price;
            }
            price = "$0." + price;
        }
        labelNames.add(price);

        return labelNames;
    }

    private java.util.List<Component> getComponentList(NIStock stock) {
        ui.elements.Button editButton = new ui.elements.Button("Edit", this, "edit");
        ui.elements.Button deleteButton = new ui.elements.Button("Delete", this, "delete");
        Button addToCartButton = new Button("Add To Cart", this, "cart");

        List<Component> componentList = new ArrayList<>();
        componentList.add(addToCartButton);
        componentList.add(editButton);
        componentList.add(deleteButton);

        return componentList;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("edit")) {
            editNIStock();
        } else if (e.getActionCommand().equals("delete")) {
            try {
                shop.removeItemFromCatalogue(this.stock.getName());
            } catch (ItemNotFound exception) {
                //Impossible state in GUI
            }
            refreshable.refresh();
        } else if (e.getActionCommand().equals("cart")) {
            addToCart();
            refreshable.refresh();
        } else {
//
        }
    }

    private void addToCart() {
        StringEntry entry = new StringEntry();
        try {
            int amount = Integer.parseInt(entry.getString("How many?"));
            shop.addToCart(this.stock.getName(), amount);
        } catch (NotEnoughInventory e) {
            frame.sayErrorMessage(e.errorMessage());

        } catch (NotPositiveInteger e) {
            frame.sayErrorMessage(e.getMessage());
        } catch (ItemNotFound e) {
            // IMPOSSIBLE WITH GUI
        } catch (Exception e) {
            //
        }
    }

    private void editNIStock() {
        NonInventoryStockEntry entry = new NonInventoryStockEntry();
        Map<String, String> infoMap = entry.getString(this.stock);
        if (!infoMap.isEmpty()) {
            String name = infoMap.get("name");
            int price = Integer.parseInt(infoMap.get("price"));
            int cost = Integer.parseInt(infoMap.get("cost"));
            try {
                shop.editCatalogue(name,price,cost);
                this.init(getLabelNames(stock), getComponentList(stock));
                frame.update();
            } catch (NotPositiveInteger e) {
                frame.sayErrorMessage(e.getMessage());
            } catch (ItemNotFound e) {
                frame.sayErrorMessage(e.getMessage());
            }
        }
    }

}

