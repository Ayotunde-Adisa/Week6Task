package Week6Task;

import java.util.*;
import java.util.stream.Collectors;

public class Library {
    private List<Book> books;
    private PriorityQueue<BookRequest> requests;

    public Library() {
        books = new ArrayList<>();
        requests = new PriorityQueue<>(new RequestComparator());
    }

    public List<Book> getBooks() {
        return books;
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void requestBook(Person person, String bookTitle) {
        books.stream()
                .filter(book -> book.getTitle().equals(bookTitle))
                .findFirst()
                .ifPresentOrElse(
                        book -> requests.add(new BookRequest(person, book)),
                        () -> System.out.println("Book not found in library.")
                );
    }

    public List<String> processRequests() {
        List<String> messages = new ArrayList<>();
        while (!requests.isEmpty()) {
            BookRequest request = requests.poll();
            Book book = request.getBook();
            if (book.getCopies() > 0) {
                book.borrowCopy();
                messages.add(request.getRequester().getName() + " has borrowed " + book.getTitle());
            } else {
                messages.add("Book Taken: " + request.getRequester().getName() + " requested " + book.getTitle() + " but it is already borrowed.");
            }
        }
        return messages;
    }
}
