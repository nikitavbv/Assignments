use crate::state::{GameState, derive_states, GameField, Position, get_snake_head};
use crate::eval::eval_state;
use std::cmp::{max, min};
use std::time::Instant;

pub fn minimax(field: &GameField, state: &GameState, depth: u32, alpha_initial: i32,
               beta_initial: i32, ai_turn: bool, started_at: Instant, max_time: u128) -> (i32, Position) {
    let mut alpha = alpha_initial;
    let mut beta = beta_initial;

    if depth == 0 || state.is_game_over(field) || started_at.elapsed().as_millis() > max_time {
        return (eval_state(field, state), get_snake_head(&state, ai_turn));
    }

    if ai_turn {
        // maximizing
        let mut max_eval: i32 = std::i32::MIN;
        let mut best_move = get_snake_head(&state, ai_turn);
        let derived_states = derive_states(state, ai_turn, field);
        for state in derived_states {
            let (eval, _) = minimax(field, &state, depth - 1, alpha, beta, !ai_turn, started_at, max_time);
            if eval > max_eval {
                max_eval = eval;
                best_move = get_snake_head(&state, ai_turn);
            }
            alpha = max(alpha, eval);
            if beta <= alpha {
                break
            }
        }

        (max_eval, best_move)
    } else {
        let mut min_eval: i32 = std::i32::MAX;
        let mut best_move = get_snake_head(&state, ai_turn);
        let derived_states = derive_states(state, ai_turn, field);
        for state in derived_states {
            let (eval, _) = minimax(field, &state, depth - 1, alpha, beta, !ai_turn, started_at, max_time);
            if eval < min_eval {
                min_eval = eval;
                best_move = get_snake_head(&state, ai_turn);
            }
            beta = min(beta, eval);
            if beta <= alpha {
                break
            }
        }

        (min_eval, best_move)
    }
}