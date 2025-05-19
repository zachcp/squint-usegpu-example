(ns LiveComponent
  (:require ["@use-gpu/live" :refer [LC]]
            ["@use-gpu/webgpu" :refer [WebGPU]]
            ["@use-gpu/workbench" :refer [DebugProvider FontLoader PanControls FlatCamera Pass ImageTexture]]
            ["@use-gpu/layout" :refer [UI Layout Flex Block Inline Text]]
            ["@use-gpu/inspect" :refer [UseInspect]]
            ["@use-gpu/inspect-gpu" :refer [inspectGPU]]
            ;; ["./wgsl/test.wgsl" :refer [wgslFunction]]
            ))

(def FONTS [{:family "Lato" :weight "black" :style "normal" :src "/Lato-Black.ttf"}])

(defn Camera [props]
  (let [children (.-children props)]
    #jsx [PanControls
          (fn [x y zoom]
            #jsx [FlatCamera {:x x :y y :zoom zoom} children])]))

(set! (.-displayName Camera) "Camera")

;; This is a Live component that takes a canvas element
(defn LiveComponent [props]
  (let [canvas (.-canvas props)]
    #jsx [UseInspect {:provider DebugProvider :extensions [inspectGPU]}
          [WebGPU {:canvas canvas}
           [FontLoader {:fonts FONTS}
            [Camera
             [Pass
              [UI
               [Layout
                [Flex {:width "100%"  :height "100%" :align "center"}
                 [Flex {:width 500 :height 150 :fill "#3090ff" :align "center" :direction "y"}
                  [Inline {:align "center"}
                   [Text {:weight "black"  :size 48  :lineHeight 64  :color "#ffffff"}
                    "-~ Use.GPU ~-"]]
                  [Inline {:align "center"}
                   [Text {:weight "black" :size 16 :lineHeight 64 :color "#ffffff" :opacity 0.5}
                    "Zoom Me"]]
                  [ImageTexture {:url "/test.png" :colorSpace "srgb"}
                   (fn [texture]
                     #jsx [Flex {:align "center" :width "100%" :height 150}
                           [Block {:fill "#3090ff"
                                   :width 150
                                   :height 150
                                   :margin 20
                                   :texture texture
                                   :image {:fit "scale"}}]])]]]]]]]]]]))

(set! (.-displayName LiveComponent) "LiveComponent")

(def ^:export Component LiveComponent)
