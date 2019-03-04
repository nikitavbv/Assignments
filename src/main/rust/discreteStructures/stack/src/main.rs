use std::io;

mod my_stack_trait;
mod my_fixed_stack;
mod my_dynamic_stack;

use my_stack_trait::MyStack;
use my_fixed_stack::MyFixedStack;
use my_dynamic_stack::MyDynamicStack;

fn main() {
    let fixed_stack: MyFixedStack<char> = MyFixedStack::with_default_capacity();
    let dynamic_stack: MyDynamicStack<char> = MyDynamicStack::new();

    let brackets_sequence = request_brackets_sequence();
    println!(
        "Sequence is correct (fixed stack): {}",
        check_if_sequence_is_correct(&brackets_sequence, fixed_stack)
    );
    println!(
        "Sequence is correct (dynamic stack): {}",
        check_if_sequence_is_correct(&brackets_sequence, dynamic_stack)
    )
}

fn request_brackets_sequence() -> String {
    println!("Enter brackets sequence: ");
    let mut input = String::new();
    io::stdin()
        .read_line(&mut input)
        .ok()
        .expect("Can't read user input");
    input
}

fn check_if_sequence_is_correct(brackets_sequence: &String, mut stack: impl MyStack<char>) -> bool {
    for element in brackets_sequence.trim().chars() {
        if element == '(' {
            stack.push(element);
        } else if element == ')' {
            if stack.is_empty() {
                return false
            }

            stack.pop();
        } else {
            panic!("Unknown character: {}", element);
        }
    }

    stack.is_empty()
}