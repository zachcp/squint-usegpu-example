(ns Fallback
  (:require ["react" :as react]))

(defn makeFallback [error]
  (fn []
    #jsx [:div {:style {:display "flex"
                        :flexDirection "column"
                        :alignItems "center"
                        :justifyContent "center"
                        :height "100%"
                        :width "100%"
                        :padding "2rem"
                        :color "#FF3A33"
                        :backgroundColor "#121212"
                        :fontFamily "monospace"
                        :textAlign "center"}}
          [:h2 "USE.GPU Error"]
          [:p "There was an error initializing the GPU context:"]
          [:pre {:style {:maxWidth "100%"
                          :overflow "auto"
                          :padding "1rem"
                          :backgroundColor "#222"
                          :borderRadius "4px"
                          :margin "1rem 0"}}
           (str error)]
          [:p "Please check that your browser supports WebGPU and that it is enabled."]]))

(def ^:export makeFallback makeFallback)