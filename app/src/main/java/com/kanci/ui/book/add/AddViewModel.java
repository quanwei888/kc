package com.kanci.ui.book.add;


public class AddViewModel {
    /*
    public static interface View extends BaseViewModel.View {
        void showBookList(BookListAdapter myAdapter, Map<String, BookListAdapter> adapters);
    }

    public AddViewModel.View view;

    public Map<String, List<Book>> books = new HashMap<>();
    public List<Book> myBooks = new ArrayList<>();
    public Map<String, BookListAdapter> adapters = new HashMap<>();
    public BookListAdapter myAdapter = new BookListAdapter();;


    @Inject
    public AddViewModel() {
    }

    public void loadData() {
        Single<List<Book>> single = Single.create(emitter -> {
            List<Book> bookList = getDataManager().getBookList();
            emitter.onSuccess(bookList);
        });

        CompositeDisposable compositeDisposable = new CompositeDisposable();
        Disposable disposable = single.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        result -> {
                            //Book List
                            for (Book book : result) {
                                if (!books.containsKey(book.tag)) {
                                    books.put(book.tag, new ArrayList<>());
                                }
                                books.get(book.tag).add(book);
                            }
                            for (String key : books.keySet()) {
                                BookListAdapter adapter = new BookListAdapter();
                                adapter.addAll(books.get(key));
                                adapters.put(key, adapter);
                            }

                            //My Book
                            for (Book book : result) {
                                if (book.isFavor) {
                                    myBooks.add(book);
                                }
                            }
                            myAdapter.addAll(myBooks);

                            view.showBookList(myAdapter, adapters);
                        },
                        error -> {
                            view.handleError(error);
                        }
                );
        compositeDisposable.add(disposable);
    }
    */

}
