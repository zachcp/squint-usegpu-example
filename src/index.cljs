;; The lauch script needs to be async fro use.gpu to load
;;
(ns index
  (:require ["react-dom/client" :as ReactDOM]))

(defn init []
  (js/console.log "Initializing app...")
  (-> (js/import "./App")
      (.then (fn [module]
               (let [App (.-App module)
                     mountElement (js/document.getElementById "use-gpu")
                     root (ReactDOM/createRoot mountElement)]
                 (js/console.log "App component loaded:" App)
                 (.render root #jsx [App]))))))

(init)
