package com.nikitavbv.assignments.oop.lab1;

import com.nikitavbv.assignments.oop.lab1.input.MenuInput;
import com.nikitavbv.assignments.oop.lab1.controllers.ApartmentController;
import com.nikitavbv.assignments.oop.lab1.controllers.MenuController;
import com.nikitavbv.assignments.oop.lab1.util.ApartmentProvider;
import com.nikitavbv.assignments.oop.lab1.util.RandomApartmentProvider;
import com.nikitavbv.assignments.oop.lab1.views.ApartmentsView;
import com.nikitavbv.assignments.oop.lab1.input.ApartmentSearchUserInput;
import com.nikitavbv.assignments.oop.lab1.views.MenuView;
import java.util.Random;

public class Lab1 {

  private static final ApartmentProvider APARTMENT_PROVIDER = new RandomApartmentProvider(new Random(), 40);

  private static final ApartmentsView APARTMENTS_VIEW = new ApartmentsView(System.out, System.err);
  private static final ApartmentSearchUserInput USER_INPUT = new ApartmentSearchUserInput(System.out, System.err, System.in);
  private static final ApartmentController MAIN_CONTROLLER = new ApartmentController(APARTMENT_PROVIDER, APARTMENTS_VIEW, USER_INPUT);

  private static final MenuView MENU_VIEW = new MenuView(System.out, System.err);
  private static final MenuInput MENU_INPUT = new MenuInput(System.out, System.err, System.in);

  public static void main(String[] args) {
    new MenuController(
            MENU_VIEW,
            MENU_INPUT,
            MAIN_CONTROLLER
    ).run();
  }
}
