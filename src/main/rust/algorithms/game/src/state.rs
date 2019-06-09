use crate::eval::get_total_possible_cells;

pub struct GameField {
    pub width: u32,
    pub height: u32
}

#[derive(Copy, Clone, Eq, PartialEq)]
pub struct Position {
    pub x: u32,
    pub y: u32
}

#[derive(Clone)]
pub struct GameState {
    pub ai_snake: Vec<Position>,
    pub player_snake: Vec<Position>
}

impl GameState {
    pub fn is_game_over(&self, field: &GameField) -> bool {
        return get_total_possible_cells(field, self, false) == 0
            || get_total_possible_cells(field, self, true) == 0
    }
}

pub fn init_game(field: &GameField, ai_is_blue: bool) -> GameState {
    if ai_is_blue {
        GameState {
            ai_snake: vec![
                Position { x: 1, y: 1}
            ],
            player_snake: vec![
                Position { x: field.width - 2, y: field.height - 2 }
            ]
        }
    } else {
        GameState {
            ai_snake: vec![
                Position { x: field.width - 2, y: field.height - 2 }
            ],
            player_snake: vec![
                Position { x: 1, y: 1}
            ]
        }
    }
}

pub fn derive_states(state: &GameState, ai_move: bool, field: &GameField) -> Vec<GameState> {
    get_possible_moves(&get_snake_head(&state, ai_move), state, &field)
        .into_iter()
        .map(|mov| {
            let mut ai_new_moves = state.ai_snake.clone();
            let mut player_new_moves = state.player_snake.clone();
            if ai_move {
                ai_new_moves.push(mov);
            } else {
                player_new_moves.push(mov);
            }
            GameState {
                ai_snake: ai_new_moves,
                player_snake: player_new_moves
            }
        }).collect()
}

pub fn get_snake_head(state: &GameState, ai_move: bool) -> Position {
    return if ai_move {
        state.ai_snake[state.ai_snake.len() - 1]
    } else {
        state.player_snake[state.player_snake.len() - 1]
    };
}

pub fn get_possible_moves(head: &Position, state: &GameState, field: &GameField) -> Vec<Position> {
    get_adjacent_cells(&field, head)
        .into_iter()
        .filter(|pos| is_empty_cell(state, pos))
        .collect()
}

pub fn get_adjacent_cells(field: &GameField, head: &Position) -> Vec<Position> {
    let mut cells: Vec<Position> = vec![];

    if head.x < field.width - 1 {
        cells.push(Position {
            x: head.x + 1,
            y: head.y,
        });
    }
    if head.x > 0 {
        cells.push(Position {
            x: head.x - 1,
            y: head.y
        });
    }
    if head.y > 0 {
        cells.push(Position {
            x: head.x, y: head.y - 1
        });
    }
    if head.y < field.height - 1 {
        cells.push(Position {
            x: head.x, y: head.y + 1
        });
    }

    cells
}

fn is_empty_cell(state: &GameState, cell: &Position) -> bool {
    for pos in  state.ai_snake.clone() {
        if pos_eq(&pos, cell) {
            return false
        }
    }

    for pos in state.player_snake.clone() {
        if pos_eq(&pos, cell) {
            return false
        }
    }

    true
}

fn pos_eq(a: &Position, b: &Position) -> bool {
    a.x == b.x && a.y == b.y
}