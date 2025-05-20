(ns App
  (:require
   ["@use-gpu/react" :refer [LiveCanvas]]
   ["@use-gpu/plot" :refer [Plot, Point, Line, Transform]]
   ["@use-gpu/webgpu" :refer [AutoCanvas, WebGPU]]
   ["@use-gpu/workbench" :refer [DebugProvider FontLoader PanControls FlatCamera Pass ImageTexture]]
   ["@use-gpu/layout" :refer [UI Layout Flex Block Inline Text]]
   ["@use-gpu/inspect" :refer [UseInspect]]
   ["@use-gpu/inspect-gpu" :refer [inspectGPU]]
   ["react" :as react]))

(def FONTS [{:family "Lato" :weight "black" :style "normal" :src "./Lato-Black.ttf"}])

;; Sidebar component for traditional UI
(defn Sidebar []
  #jsx [:div {:className "sidebar"}
        [:h2 "USE.GPU Demo"]
        [:p "This demo shows two canvas elements using USE.GPU."]
        [:hr]
        [:h3 "Canvas Controls"]
        [:p "Use mouse wheel to zoom in/out."]
        [:p "Click and drag to pan around."]
        [:hr]
        [:h3 "Top Canvas"]
        [:p "Shows a simple point plot."]
        [:hr]
        [:h3 "Bottom Canvas"]
        [:p "Shows UI elements and textures."]])

;; A Canvas component specifically for the top canvas
(defn TopCanvas []
  (js/console.log "in the TopCanvas component")
  #jsx [LiveCanvas
        {:fallback (fn [error]  #jsx [:div "Error in top canvas: " (str error)])
         :width "100%" :height "100%"}
        (fn [canvas] #jsx [TopCanvasContent {:canvas canvas}])])

;; A Canvas component specifically for the bottom canvas
(defn BottomCanvas []
  (js/console.log "in the BottomCanvas component")
  #jsx [LiveCanvas
        {:fallback (fn [error]  #jsx [:div "Error in bottom canvas: " (str error)])
         :width "100%" :height "100%"}
        (fn [canvas] #jsx [BottomCanvasContent {:canvas canvas}])])

;; Main App component that structures the layout
(defn App []
  (js/console.log "in the App")
  #jsx [:div {:id "use-gpu-app" :style {:display "flex" :width "100%" :height "100%"}}
        [Sidebar]
        [:div {:className "main-content"}
         [:div {:className "canvas-container"}
          [:div {:className "canvas-top"}
           [TopCanvas]]
          [:div {:className "canvas-bottom"}
           [TopCanvas]]]]])

(set! (.-displayName App) "App")

(defn Camera [props]
  (let [children (.-children props)]
    #jsx [PanControls {:active true}
          (fn [x y zoom]
            #jsx [FlatCamera {:x x :y y :zoom zoom} children])]))

(set! (.-displayName Camera) "Camera")

;; Component for the top canvas content - focused on points and plots
(defn TopCanvasContent [props]
  (let [canvas (.-canvas props)]
    (js/console.log "in the TopCanvasContent" "canvas:" canvas)
    #jsx [UseInspect {:provider DebugProvider :extensions [inspectGPU]}
          [WebGPU
           [AutoCanvas {:canvas canvas}
            [FontLoader {:fonts FONTS}
             [Camera
              [Pass
               [Plot
                [Transform {:position [100 100]} [Point {:position [50 50] :size 200 :color "#ffffff"}]]
                [Point
                 {:positions [[50 100] [50 130] [50 160] [50 190] [50 220]]
                  :size 10 :color "#3090ff"}]]
               [UI
                [Layout
                 [Flex {:width "100%"  :height "100%" :align "center"}
                  [Flex {:width 500 :height 150 :fill "#3090ff" :align "center" :direction "y"}
                   [Inline {:align "center"}
                    [Text {:weight "black"  :size 48  :lineHeight 64  :color "#ffffff"} "-~ Use.GPU ~-"]]
                   [Inline {:align "center"}
                    [Text {:weight "black" :size 16 :lineHeight 64 :color "#ffffff" :opacity 0.5} "Bottom Canvas"]]
                   [ImageTexture {:url "./test.png" :colorSpace "srgb"}
                    (fn [texture]
                      (js/console.log "in the texture callback, texture:", texture)
                      #jsx [Flex {:align "center" :width "100%" :height 150}
                            [Block
                             {:fill "#3090ff" :width 150 :height 150 :margin 20
                              :texture texture :image {:fit "scale"}}]])]]]]]]]]]]]))

(set! (.-displayName Sidebar) "Sidebar")
(set! (.-displayName TopCanvas) "TopCanvas")

(def ^:export App App)
