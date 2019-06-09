#![feature(proc_macro_hygiene, decl_macro)]
#[macro_use] extern crate rocket;
#[macro_use] extern crate rocket_contrib;
#[macro_use] extern crate serde_derive;

mod state;
mod eval;
mod minimax;

use rocket_contrib::serve::StaticFiles;

use crate::state::{GameField, Position, GameState};
use crate::eval::{get_total_possible_cells};
use crate::minimax::minimax;
use rocket_contrib::json::{Json, JsonValue};
use std::time::Instant;

const MAX_TURN_TIME: u128 = 2500;
const MAX_FIELD_SIZE: u32 = 16;

#[derive(Deserialize)]
struct NextTurnRequest {
    width: u32,
    height: u32,
    player_snake: Vec<Vec<u32>>,
    ai_snake: Vec<Vec<u32>>,
    difficulty: u32
}

fn main() {
    rocket::ignite()
        .mount("/", StaticFiles::from("static"))
        .mount("/api", routes![index])
        .launch();
}

#[post("/game", data="<input>")]
fn index(input: Json<NextTurnRequest>) -> JsonValue {
    let width = input.width;
    let height= input.height;
    let player_snake: Vec<Position> = input.player_snake.clone()
        .into_iter()
        .map(|pos| Position {
            x: pos.get(0).expect("no x coordinate for player point").clone(),
            y: pos.get(1).expect("no y coordinate for player point").clone()
        })
        .collect();
    let ai_snake: Vec<Position> = input.ai_snake.clone()
        .into_iter()
        .map(|pos| Position {
            x: pos.get(0).expect("no x coordinate for ai point").clone(),
            y: pos.get(1).expect("no y coordinate for ai point").clone()
        })
        .collect();
    let difficulty: u32 = input.difficulty.clone() as u32;

    if width > MAX_FIELD_SIZE && height > MAX_FIELD_SIZE {
        return json!({
            "status": "invalid_dimensions"
        });
    }

    let field = GameField {
        width,
        height
    };
    let state = GameState {
        ai_snake,
        player_snake
    };

    let ai_moves = get_total_possible_cells(&field, &state, true);
    let player_moves = get_total_possible_cells(&field, &state, false);

    if ai_moves == 0 {
        return json!({
            "status": "player_win"
        });
    } else if player_moves == 0 {
        return json!({
            "status": "ai_win"
        });
    }

    let now = Instant::now();
    let (_, pos) = minimax(&field, &state, difficulty, std::i32::MIN, std::i32::MAX, true, now, MAX_TURN_TIME);

    json!({
        "x": pos.x,
        "y": pos.y,
        "status": "active",
        "time": now.elapsed().as_millis() as u32,
    })
}