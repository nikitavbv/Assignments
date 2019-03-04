use crate::my_stack_trait::MyStack;

struct MyStackNode<T: Copy> {
    element: T,
    next: Option<Box<MyStackNode<T>>>
}

pub struct MyDynamicStack<T: Copy> {
    top: Option<Box<MyStackNode<T>>>,
    size: usize
}

impl <T: Copy> MyDynamicStack<T> {

    pub fn new() -> MyDynamicStack<T> {
        MyDynamicStack {
            top: None,
            size: 0
        }
    }

}

impl <T: Copy> MyStack<T> for MyDynamicStack<T> {
    fn push(&mut self, element: T) {
        self.top = Some(Box::new(MyStackNode {
            element,
            next: self.top.take()
        }));

        self.size += 1;
    }

    fn pop(&mut self) -> Option<T> {
        if self.is_empty() {
            return None
        }

        match self.top.take() {
            None => None,
            Some(mut top) => {
                self.top = top.next.take();
                self.size -= 1;
                Some(top.element)
            }
        }
    }

    fn top(&mut self) -> Option<T> {
        match self.top.take() {
            None => None,
            Some(top) => {
                Some(top.element)
            }
        }
    }

    fn clear(&mut self) {
        self.top = None;
        self.size = 0;
    }

    fn is_empty(&self) -> bool {
        self.get_size() == 0
    }

    fn get_size(&self) -> usize {
        self.size
    }
}