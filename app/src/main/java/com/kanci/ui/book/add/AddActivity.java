package com.kanci.ui.book.add;

import com.kanci.ui.base.BaseActivity;

public class AddActivity {
/*
    public AddViewModel vm;
    public ActivityBookAdd1Binding binding;

    public static Intent newIntent(Context context) {
        return new Intent(context, AddActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_book_add);
        binding.setVariable(BR.vm, vm);
        binding.executePendingBindings();
        setup();
    }

    protected void setup() {
        vm.loadData();
    }

    @Override
    public void showBookList(BookListAdapter myAdapter, Map<String, BookListAdapter> adapters) {
        //My Book
        AppGridLayoutManager myLayoutManager;
        RecyclerView lv;
        AppGridLayoutManager layoutManager;


        lv = binding.mybookView;
        layoutManager = new AppGridLayoutManager(this, 1, GridLayoutManager.HORIZONTAL, false);
        lv.setLayoutManager(layoutManager);
        lv.setAdapter(myAdapter);
        lv.setNestedScrollingEnabled(false);
        myAdapter.setOnItemClickListener((v) -> {
                    Book book = (Book) v.getTag();
                    ActivityMgr.gotoBookPlan(this, book);
                }
        );


        //Book List
        for (String key : adapters.keySet()) {
            View tagButton = LayoutInflater.from(this).inflate(R.layout.view_tag_button, null);
            ((Button) tagButton.findViewById(R.id.button)).setText(key);
            binding.tagView.addView(tagButton);

            lv = new RecyclerView(this);
            layoutManager = new AppGridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);
            //((AppGridLayoutManager) layoutManager).setScrollEnabled(false);
            lv.setLayoutManager(layoutManager);
            lv.setAdapter(adapters.get(key));
            lv.setNestedScrollingEnabled(false);
            adapters.get(key).setOnItemClickListener((v) -> {
                        Book book = (Book) v.getTag();
                    }
            );

            View tv = LayoutInflater.from(this).inflate(R.layout.view_book_tag, null);
            ((TextView) tv.findViewById(R.id.tag)).setText(key);
            binding.bookGroup.addView(tv);
            binding.bookGroup.addView(lv);
        }
    }
    */
}
