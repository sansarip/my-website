(ns my-website.views.home.panel
  (:require
    [re-frame.core :as re-frame]
    [my-website.styles :refer [color-palette screen-sizes font-sizes spacing-sizes]]
    [my-website.utilities :refer [seq->css-grid-areas word-concat]]
    [my-website.components.flexbox :refer [flexbox]]
    [my-website.components.image :refer [image]]
    [my-website.views.home.styles :as styles]
    [spade.core :refer [defclass]]))

(defn home-panel []
  [:> flexbox {:extraClasses  (styles/container-class)
               :justify       "between"
               :align         "center"
               :justify-items "center"
               :align-items   "start"
               :wrap          "nowrap"}
   [:div {:class (styles/description-class)}
    [:h1 {:class "inverse"}
     "Hi there, I'm Sepehr!"]
    [:p {:class "inverse"}
     "Lorem ipsum dolor amet fanny pack williamsburg tbh raw denim air plant.
     taxidermy scenester selvage man braid hammock lyft occupy. 8-bit pickled +1
     artisan tacos literally enamel pin kinfolk chicharrones glossier. 8-bit taxidermy
     distillery authentic everyday carry flannel. Ethical af prism green juice plaid iceland,
     truffaut deep v quinoa irony try-hard tbh PBR&B bushwick. DIY intelligentsia asymmetrical
     brunch church-key slow-carb aesthetic air plant franzen vice. Freegan cold-pressed tote bag
     migas slow-carb DIY four loko, woke fixie quinoa adaptogen master cleanse."]]
   [:> image {:extraClasses (word-concat (styles/image-class))
              :src          "assets/figgy.png"}]])
