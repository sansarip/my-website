(use 'clostache.parser)

(let [games-path "content/markdown/games"
      work-path "content/markdown/work"]
  (def data {:bearly-iced-description      (slurp (str games-path "/bearly-iced-description.md"))
             :bearly-iced-more-info        (slurp (str games-path "/bearly-iced-more-info.md"))
             :catching-clouds-description  (slurp (str games-path "/catching-clouds-description.md"))
             :catching-clouds-more-info    (slurp (str games-path "/catching-clouds-more-info.md"))
             :click-clack-description      (slurp (str games-path "/click-clack-description.md"))
             :click-clack-more-info        (slurp (str games-path "/click-clack-more-info.md"))
             :fp-description               (slurp (str work-path "/functional-programming-description.md"))
             :containerization-description (slurp (str work-path "/containerization-description.md"))}))

(def content-maps
  [{:from "templates/my_website/views/games/content.cljs"
    :to   "src/cljs/my_website/views/games/content.cljs"}
   {:from "templates/my_website/views/work/content.cljs"
    :to   "src/cljs/my_website/views/work/content.cljs"}])

(doseq [{:keys [from to]} content-maps]
  (->> data
       (render (slurp from))
       (spit to)))