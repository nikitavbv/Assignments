use crate::my_stack_trait::MyStack;

pub struct MyFixedStack<T: Copy> {
    arr: Box<[Option<T>]>,
    pos: usize,
    capacity: usize,
}

impl <T: Copy> MyFixedStack<T> {
    const DEFAULT_CAPACITY: usize = 100;

    pub fn new(capacity: usize) -> MyFixedStack<T> {
        let mut vec = Vec::with_capacity(capacity);
        for _i in 0..capacity {
            vec.push(None);
        }
        MyFixedStack {
            arr: vec.into_boxed_slice(),
            pos: 0,
            capacity
        }
    }

    pub fn with_default_capacity() -> MyFixedStack<T> {
        Self::new(Self::DEFAULT_CAPACITY)
    }
}

impl <T: Copy> MyStack<T> for MyFixedStack<T> {
    fn push(&mut self, element: T) {
        if self.pos >= self.capacity {
            panic!("Stack is full")
        }

        self.arr[self.pos] = Some(element);
        self.pos += 1;
    }

    fn pop(&mut self) -> Option<T> {
        if self.is_empty() {
            return None
        }

        self.pos -= 1;
        self.arr[self.pos]
    }

    fn top(&mut self) -> Option<T> {
        if self.is_empty() {
            return None
        }

        self.arr[self.pos - 1]
    }

    fn clear(&mut self) {
        self.pos = 0;
    }

    fn is_empty(&self) -> bool {
        self.get_size() == 0
    }

    fn get_size(&self) -> usize {
        self.pos
    }
}