package com.example.customrecycle.frame.retrofit;

import com.google.gson.annotations.Expose;

import java.util.List;

public class UserEntity {


    /**
     * count : 10
     * start : 0
     * total : 250
     * subjects : [{"rating":{"max":10,"average":9.2,"stars":"50","min":0},"genres":["剧情","动作","科幻"],"title":"盗梦空间","casts":[{"alt":"https://movie.douban.com/celebrity/1041029/","avatars":{"small":"https://img3.doubanio.com/img/celebrity/small/470.jpg","large":"https://img3.doubanio.com/img/celebrity/large/470.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/470.jpg"},"name":"莱昂纳多·迪卡普里奥","id":"1041029"},{"alt":"https://movie.douban.com/celebrity/1101703/","avatars":{"small":"https://img1.doubanio.com/img/celebrity/small/3517.jpg","large":"https://img1.doubanio.com/img/celebrity/large/3517.jpg","medium":"https://img1.doubanio.com/img/celebrity/medium/3517.jpg"},"name":"约瑟夫·高登-莱维特","id":"1101703"},{"alt":"https://movie.douban.com/celebrity/1012520/","avatars":{"small":"https://img1.doubanio.com/img/celebrity/small/118.jpg","large":"https://img1.doubanio.com/img/celebrity/large/118.jpg","medium":"https://img1.doubanio.com/img/celebrity/medium/118.jpg"},"name":"艾伦·佩吉","id":"1012520"}],"collect_count":950778,"original_title":"Inception","subtype":"movie","directors":[{"alt":"https://movie.douban.com/celebrity/1054524/","avatars":{"small":"https://img3.doubanio.com/img/celebrity/small/673.jpg","large":"https://img3.doubanio.com/img/celebrity/large/673.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/673.jpg"},"name":"克里斯托弗·诺兰","id":"1054524"}],"year":"2010","images":{"small":"https://img3.doubanio.com/view/movie_poster_cover/ipst/public/p513344864.jpg","large":"https://img3.doubanio.com/view/movie_poster_cover/lpst/public/p513344864.jpg","medium":"https://img3.doubanio.com/view/movie_poster_cover/spst/public/p513344864.jpg"},"alt":"https://movie.douban.com/subject/3541415/","id":"3541415"},{"rating":{"max":10,"average":9.3,"stars":"50","min":0},"genres":["喜剧","爱情","科幻"],"title":"机器人总动员","casts":[{"alt":"https://movie.douban.com/celebrity/1009535/","avatars":{"small":"https://img1.doubanio.com/img/celebrity/small/13028.jpg","large":"https://img1.doubanio.com/img/celebrity/large/13028.jpg","medium":"https://img1.doubanio.com/img/celebrity/medium/13028.jpg"},"name":"本·贝尔特","id":"1009535"},{"alt":"https://movie.douban.com/celebrity/1000389/","avatars":{"small":"https://img3.doubanio.com/img/celebrity/small/1365856130.16.jpg","large":"https://img3.doubanio.com/img/celebrity/large/1365856130.16.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/1365856130.16.jpg"},"name":"艾丽莎·奈特","id":"1000389"},{"alt":"https://movie.douban.com/celebrity/1018022/","avatars":{"small":"https://img1.doubanio.com/img/celebrity/small/31068.jpg","large":"https://img1.doubanio.com/img/celebrity/large/31068.jpg","medium":"https://img1.doubanio.com/img/celebrity/medium/31068.jpg"},"name":"杰夫·格尔林","id":"1018022"}],"collect_count":615589,"original_title":"WALL·E","subtype":"movie","directors":[{"alt":"https://movie.douban.com/celebrity/1036450/","avatars":{"small":"https://img3.doubanio.com/img/celebrity/small/1467359656.96.jpg","large":"https://img3.doubanio.com/img/celebrity/large/1467359656.96.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/1467359656.96.jpg"},"name":"安德鲁·斯坦顿","id":"1036450"}],"year":"2008","images":{"small":"https://img3.doubanio.com/view/movie_poster_cover/ipst/public/p449665982.jpg","large":"https://img3.doubanio.com/view/movie_poster_cover/lpst/public/p449665982.jpg","medium":"https://img3.doubanio.com/view/movie_poster_cover/spst/public/p449665982.jpg"},"alt":"https://movie.douban.com/subject/2131459/","id":"2131459"}]
     * title : 豆瓣电影Top250
     */

    @Expose
    private int count;
    @Expose
    private int start;
    @Expose
    private int total;
    @Expose
    private String title;
    @Expose
    private List<SubjectsBean> subjects;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<SubjectsBean> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<SubjectsBean> subjects) {
        this.subjects = subjects;
    }

    public static class SubjectsBean {
        /**
         * rating : {"max":10,"average":9.2,"stars":"50","min":0}
         * genres : ["剧情","动作","科幻"]
         * title : 盗梦空间
         * casts : [{"alt":"https://movie.douban.com/celebrity/1041029/","avatars":{"small":"https://img3.doubanio.com/img/celebrity/small/470.jpg","large":"https://img3.doubanio.com/img/celebrity/large/470.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/470.jpg"},"name":"莱昂纳多·迪卡普里奥","id":"1041029"},{"alt":"https://movie.douban.com/celebrity/1101703/","avatars":{"small":"https://img1.doubanio.com/img/celebrity/small/3517.jpg","large":"https://img1.doubanio.com/img/celebrity/large/3517.jpg","medium":"https://img1.doubanio.com/img/celebrity/medium/3517.jpg"},"name":"约瑟夫·高登-莱维特","id":"1101703"},{"alt":"https://movie.douban.com/celebrity/1012520/","avatars":{"small":"https://img1.doubanio.com/img/celebrity/small/118.jpg","large":"https://img1.doubanio.com/img/celebrity/large/118.jpg","medium":"https://img1.doubanio.com/img/celebrity/medium/118.jpg"},"name":"艾伦·佩吉","id":"1012520"}]
         * collect_count : 950778
         * original_title : Inception
         * subtype : movie
         * directors : [{"alt":"https://movie.douban.com/celebrity/1054524/","avatars":{"small":"https://img3.doubanio.com/img/celebrity/small/673.jpg","large":"https://img3.doubanio.com/img/celebrity/large/673.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/673.jpg"},"name":"克里斯托弗·诺兰","id":"1054524"}]
         * year : 2010
         * images : {"small":"https://img3.doubanio.com/view/movie_poster_cover/ipst/public/p513344864.jpg","large":"https://img3.doubanio.com/view/movie_poster_cover/lpst/public/p513344864.jpg","medium":"https://img3.doubanio.com/view/movie_poster_cover/spst/public/p513344864.jpg"}
         * alt : https://movie.douban.com/subject/3541415/
         * id : 3541415
         */

        @Expose
        private RatingBean rating;
        @Expose
        private String title;
        @Expose
        private int collect_count;
        @Expose
        private String original_title;
        @Expose
        private String subtype;
        @Expose
        private String year;
        @Expose
        private ImagesBean images;
        @Expose
        private String alt;
        @Expose
        private String id;
        @Expose
        private List<String> genres;
        @Expose
        private List<CastsBean> casts;
        @Expose
        private List<DirectorsBean> directors;

        public RatingBean getRating() {
            return rating;
        }

        public void setRating(RatingBean rating) {
            this.rating = rating;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getCollect_count() {
            return collect_count;
        }

        public void setCollect_count(int collect_count) {
            this.collect_count = collect_count;
        }

        public String getOriginal_title() {
            return original_title;
        }

        public void setOriginal_title(String original_title) {
            this.original_title = original_title;
        }

        public String getSubtype() {
            return subtype;
        }

        public void setSubtype(String subtype) {
            this.subtype = subtype;
        }

        public String getYear() {
            return year;
        }

        public void setYear(String year) {
            this.year = year;
        }

        public ImagesBean getImages() {
            return images;
        }

        public void setImages(ImagesBean images) {
            this.images = images;
        }

        public String getAlt() {
            return alt;
        }

        public void setAlt(String alt) {
            this.alt = alt;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public List<String> getGenres() {
            return genres;
        }

        public void setGenres(List<String> genres) {
            this.genres = genres;
        }

        public List<CastsBean> getCasts() {
            return casts;
        }

        public void setCasts(List<CastsBean> casts) {
            this.casts = casts;
        }

        public List<DirectorsBean> getDirectors() {
            return directors;
        }

        public void setDirectors(List<DirectorsBean> directors) {
            this.directors = directors;
        }

        public static class RatingBean {
            /**
             * max : 10
             * average : 9.2
             * stars : 50
             * min : 0
             */

            @Expose
            private int max;
            @Expose
            private double average;
            @Expose
            private String stars;
            @Expose
            private int min;

            public int getMax() {
                return max;
            }

            public void setMax(int max) {
                this.max = max;
            }

            public double getAverage() {
                return average;
            }

            public void setAverage(double average) {
                this.average = average;
            }

            public String getStars() {
                return stars;
            }

            public void setStars(String stars) {
                this.stars = stars;
            }

            public int getMin() {
                return min;
            }

            public void setMin(int min) {
                this.min = min;
            }
        }

        public static class ImagesBean {
            /**
             * small : https://img3.doubanio.com/view/movie_poster_cover/ipst/public/p513344864.jpg
             * large : https://img3.doubanio.com/view/movie_poster_cover/lpst/public/p513344864.jpg
             * medium : https://img3.doubanio.com/view/movie_poster_cover/spst/public/p513344864.jpg
             */

            @Expose
            private String small;
            @Expose
            private String large;
            @Expose
            private String medium;

            public String getSmall() {
                return small;
            }

            public void setSmall(String small) {
                this.small = small;
            }

            public String getLarge() {
                return large;
            }

            public void setLarge(String large) {
                this.large = large;
            }

            public String getMedium() {
                return medium;
            }

            public void setMedium(String medium) {
                this.medium = medium;
            }
        }

        public static class CastsBean {
            /**
             * alt : https://movie.douban.com/celebrity/1041029/
             * avatars : {"small":"https://img3.doubanio.com/img/celebrity/small/470.jpg","large":"https://img3.doubanio.com/img/celebrity/large/470.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/470.jpg"}
             * name : 莱昂纳多·迪卡普里奥
             * id : 1041029
             */

            @Expose
            private String alt;
            @Expose
            private AvatarsBean avatars;
            @Expose
            private String name;
            @Expose
            private String id;

            public String getAlt() {
                return alt;
            }

            public void setAlt(String alt) {
                this.alt = alt;
            }

            public AvatarsBean getAvatars() {
                return avatars;
            }

            public void setAvatars(AvatarsBean avatars) {
                this.avatars = avatars;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public static class AvatarsBean {
                /**
                 * small : https://img3.doubanio.com/img/celebrity/small/470.jpg
                 * large : https://img3.doubanio.com/img/celebrity/large/470.jpg
                 * medium : https://img3.doubanio.com/img/celebrity/medium/470.jpg
                 */

                @Expose
                private String small;
                @Expose
                private String large;
                @Expose
                private String medium;

                public String getSmall() {
                    return small;
                }

                public void setSmall(String small) {
                    this.small = small;
                }

                public String getLarge() {
                    return large;
                }

                public void setLarge(String large) {
                    this.large = large;
                }

                public String getMedium() {
                    return medium;
                }

                public void setMedium(String medium) {
                    this.medium = medium;
                }
            }
        }

        public static class DirectorsBean {
            /**
             * alt : https://movie.douban.com/celebrity/1054524/
             * avatars : {"small":"https://img3.doubanio.com/img/celebrity/small/673.jpg","large":"https://img3.doubanio.com/img/celebrity/large/673.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/673.jpg"}
             * name : 克里斯托弗·诺兰
             * id : 1054524
             */

            @Expose
            private String alt;
            @Expose
            private AvatarsBeanX avatars;
            @Expose
            private String name;
            @Expose
            private String id;

            public String getAlt() {
                return alt;
            }

            public void setAlt(String alt) {
                this.alt = alt;
            }

            public AvatarsBeanX getAvatars() {
                return avatars;
            }

            public void setAvatars(AvatarsBeanX avatars) {
                this.avatars = avatars;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public static class AvatarsBeanX {
                /**
                 * small : https://img3.doubanio.com/img/celebrity/small/673.jpg
                 * large : https://img3.doubanio.com/img/celebrity/large/673.jpg
                 * medium : https://img3.doubanio.com/img/celebrity/medium/673.jpg
                 */

                @Expose
                private String small;
                @Expose
                private String large;
                @Expose
                private String medium;

                public String getSmall() {
                    return small;
                }

                public void setSmall(String small) {
                    this.small = small;
                }

                public String getLarge() {
                    return large;
                }

                public void setLarge(String large) {
                    this.large = large;
                }

                public String getMedium() {
                    return medium;
                }

                public void setMedium(String medium) {
                    this.medium = medium;
                }
            }
        }
    }
}
