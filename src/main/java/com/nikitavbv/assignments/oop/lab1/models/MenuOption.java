package com.nikitavbv.assignments.oop.lab1.models;

import java.util.Map;
import java.util.Optional;

public enum MenuOption {

  SHOW_ALL,
  SEARCH_BY_ROOMS,
  SEARCH_BY_AREA_AND_FLOOR,
  EXIT;

  private static final Map<String, MenuOption> COMMANDS_TO_OPTIONS = Map.of(
          "all", SHOW_ALL,
          "search_rooms", SEARCH_BY_ROOMS,
          "search_area_floor", SEARCH_BY_AREA_AND_FLOOR,
          "exit", EXIT
  );

  private static final Map<MenuOption, String> OPTIONS_TO_DESCRIPTIONS = Map.of(
          SHOW_ALL, "Show all apartments",
          SEARCH_BY_ROOMS, "Search by rooms",
          SEARCH_BY_AREA_AND_FLOOR, "Search by area and floor",
          EXIT, "Exit"
  );

  public static Optional<MenuOption> byCommand(String command) {
      return Optional.ofNullable(COMMANDS_TO_OPTIONS.get(command));
  }

  public String description() {
    Optional<String> command = command();
    if (!command.isPresent()) {
      throw new AssertionError("Could not find command for option: " + this);
    }

    Optional<String> description = Optional.ofNullable(OPTIONS_TO_DESCRIPTIONS.get(this));
    if (!description.isPresent()) {
      throw new AssertionError("Description not set for option: " + this);
    }

    return String.format("[%s]: %s", command.get(), description.get());
  }

  public Optional<String> command() {
    for (String key : COMMANDS_TO_OPTIONS.keySet()) {
      if (COMMANDS_TO_OPTIONS.get(key) == this) {
        return Optional.of(key);
      }
    }

    return Optional.empty();
  }
}
