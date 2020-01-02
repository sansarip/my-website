(ns my-website.views.work.components.work-steps.component
  (:require
    [my-website.components.steps :refer [steps]]
    [my-website.views.work.components.work-steps.styles :refer [work-steps-class]]))

(defn work-steps [items selected]
  [:> steps {:items items
             :inverse true
             :selected-key selected
             :extra-classes (work-steps-class)}])
