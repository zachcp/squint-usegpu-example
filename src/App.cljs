(ns App
  (:require
   ["@use-gpu/react" :refer [LiveCanvas]]
   ["@use-gpu/plot" :refer [Plot, Point, Line, Transform]]
   ["@use-gpu/webgpu" :refer [AutoCanvas, WebGPU]]
   ["@use-gpu/workbench" :refer [DebugProvider FontLoader PanControls FlatCamera Pass ImageTexture]]
   ["@use-gpu/layout" :refer [UI Layout Flex Block Inline Text]]
   ["@use-gpu/inspect" :refer [UseInspect]]
   ["@use-gpu/inspect-gpu" :refer [inspectGPU]]))

(def FONTS [{:family "Lato" :weight "black" :style "normal" :src "./Lato-Black.ttf"}])

;; React component that uses LiveCanvas to integrate USE.GPU
(defn  App []
  (js/console.log "in the App")
  #jsx [LiveCanvas
        {:fallback (fn [error]  #jsx [FallbackComponent {:error error}])
         :width "100%" :height "100%"}
        (fn [canvas] #jsx [LiveComponent {:canvas canvas}])])

(set! (.-displayName App) "App")

(defn Camera [props]
  (let [children (.-children props)]
    #jsx [PanControls {:active true}
          (fn [x y zoom]
            #jsx [FlatCamera {:x x :y y :zoom zoom} children])]))

(set! (.-displayName Camera) "Camera")

;; This is a Live component that takes a canvas element - matches the example from the docs
(defn LiveComponent [props]
  (let [canvas (.-canvas props)]
    (js/console.log "in the LiveComponent")
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
                    [Text {:weight "black" :size 16 :lineHeight 64 :color "#ffffff" :opacity 0.5} "Zoom Me"]]
                   [ImageTexture {:url "./test.png" :colorSpace "srgb"}
                    (fn [texture]
                      (js/console.log "in the texture callback, texture:", texture)
                      #jsx [Flex {:align "center" :width "100%" :height 150}
                            [Block
                             {:fill "#3090ff" :width 150 :height 150 :margin 20
                              :texture texture :image {:fit "scale"}}]])]]]]]]]]]]]))

(set! (.-displayName LiveComponent) "LiveComponent")

(def ^:export App App)
