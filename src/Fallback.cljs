(ns Fallback
  (:require ["react" :as react]))

(defn FallbackComponent [props]
  (let [error (.-error props)]
    #jsx [:div {:className "error-message flex-column"
                :style {:display "flex"
                        :alignItems "center"
                        :justifyContent "center"
                        :height "100%"
                        :width "100%"
                        :fontFamily "monospace"}}
          [:h2 "USE.GPU Error"]
          [:p "There was an error initializing the GPU context:"]
          [:pre {:style {:maxWidth "100%"
                         :overflow "auto"
                         :padding "1rem"
                         :backgroundColor "rgba(0,0,0,0.3)"
                         :borderRadius "4px"
                         :margin "1rem 0"}}
           (str error)]
          [:p "Please check that your browser supports WebGPU and that it is enabled."]
          [:button {:className "control-button"
                    :onClick #(js/window.location.reload)}
           "Reload Page"]]))

(def ^:export FallbackComponent FallbackComponent)
