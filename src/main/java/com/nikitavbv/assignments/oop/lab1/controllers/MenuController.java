package com.nikitavbv.assignments.oop.lab1.controllers;

import com.nikitavbv.assignments.oop.lab1.input.MenuInput;
import com.nikitavbv.assignments.oop.lab1.models.MenuOption;
import com.nikitavbv.assignments.oop.lab1.views.MenuView;
import java.util.Map;
import java.util.Optional;

public class MenuController {

  private MenuView menuView;
  private MenuInput menuInput;

  private ApartmentController mainService;

  private final Map<MenuOption, Runnable> handlers;

  public MenuController(MenuView menuView, MenuInput menuInput, ApartmentController mainService) {
    this.menuView = menuView;
    this.menuInput = menuInput;

    this.mainService = mainService;

    handlers = handlersInit();
  }

  @SuppressWarnings("InfiniteLoopStatement")
  public void run() {
    menuView.showWelcome();

    while (true) {
      menuView.showMenu();

      MenuOption menuOption = menuInput.requestMenuOption();
      Optional<Runnable> handler = handlerByOption(menuOption);

      if (handler.isPresent()) {
        handler.get().run();
      } else {
        throw new AssertionError("Runnable for option not set: " + menuOption);
      }
    }
  }

  private Optional<Runnable> handlerByOption(MenuOption menuOption) {
    return Optional.ofNullable(handlers.get(menuOption));
  }

  private Map<MenuOption, Runnable> handlersInit() {
    return Map.of(
            MenuOption.SHOW_ALL, mainService::runShowAll,
            MenuOption.SEARCH_BY_ROOMS, mainService::runSearchByRooms,
            MenuOption.SEARCH_BY_AREA_AND_FLOOR, mainService::runSearchByAreaAndFloor,
            MenuOption.EXIT, () -> System.exit(0)
    );
  }
}
