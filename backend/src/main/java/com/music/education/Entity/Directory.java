package com.music.education.Entity;

import java.util.List;

public abstract class Directory {
    public static final class InternalLink extends Directory {
        private final String title;
        private final String bookName;
        private final int pageIndex;
        private final List<Directory> children;

        public InternalLink(String title, String bookName, int pageIndex, List<Directory> children) {
            this.title = title;
            this.bookName = bookName;
            this.pageIndex = pageIndex;
            this.children = children;
        }

        public String getTitle() {
            return title;
        }

        public String getBookName() {
            return bookName;
        }

        public int getPageIndex() {
            return pageIndex;
        }

        public List<Directory> getChildren() {
            return children;
        }
    }

    public static final class ExternalURILink extends Directory {
        private final String title;
        private final String url;

        public ExternalURILink(String title, String url) {
            this.title = title;
            this.url = url;
        }

        public String getTitle() {
            return title;
        }

        public String getUrl() {
            return url;
        }
    }

    public static final class ExternalBookLink extends Directory {
        private final String title;
        private final String bookId;
        private final int pageIndex;

        public ExternalBookLink(String title, String bookId, int pageIndex) {
            this.title = title;
            this.bookId = bookId;
            this.pageIndex = pageIndex;
        }

        public String getTitle() {
            return title;
        }

        public String getBookId() {
            return bookId;
        }

        public int getPageIndex() {
            return pageIndex;
        }
    }
}