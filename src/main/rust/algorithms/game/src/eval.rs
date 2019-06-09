use crate::state::{GameField, GameState, Position, get_snake_head, get_possible_moves};

pub fn get_total_possible_cells(field: &GameField, state: &GameState, ai_move: bool) -> i32 {
    let mut been_to: Vec<Position> = vec![];
    let mut explore: Vec<Position> = vec![];
    explore.push(get_snake_head(&state, ai_move));

    while explore.len() > 0 {
        let pos = explore.remove(0);
        let explore_c = explore.clone();
        let other_pos: Vec<Position> = get_possible_moves(&pos, &state, &field)
            .into_iter()
            .filter(|pos| !been_to.contains(pos))
            .collect();
        other_pos
            .clone()
            .into_iter()
            .filter(|pos| !explore_c.contains(pos))
            .for_each(|pos| explore.push(pos));
        other_pos
            .into_iter()
            .for_each(|pos| been_to.push(pos));
    }

    been_to.len() as i32
}

pub fn eval_state(field: &GameField, state: &GameState) -> i32 {
    get_total_possible_cells(field, state, true)
        - get_total_possible_cells(field, state, false)
}