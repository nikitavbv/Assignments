pub trait MyStack<T> {

    fn push(&mut self, element: T);
    fn pop(&mut self) -> Option<T>;
    fn top(&mut self) -> Option<T>;
    fn clear(&mut self);
    fn is_empty(&self) -> bool;
    fn get_size(&self) -> usize;

}
