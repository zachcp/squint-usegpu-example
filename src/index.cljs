(ns index
  (:require ["@use-gpu/live" :refer [render]]
            ["@use-gpu/react" :refer [HTML]]
            ["@use-gpu/core" :refer [TextureSource]]
            ["@use-gpu/webgpu" :refer [AutoCanvas WebGPU]]
            ["@use-gpu/workbench" :refer [DebugProvider FontLoader PanControls FlatCamera Pass ImageTexture]]
            ["@use-gpu/layout" :refer [UI Layout Flex Block Inline Text]]
            ["@use-gpu/inspect" :refer [UseInspect]]
            ["@use-gpu/inspect-gpu" :refer [inspectGPU]]
            ["@use-gpu/inspect/theme.css"]
            ;; ["./Fallback" :refer [makeFallback]]
            ;; ["./wgsl/test.wgsl" :refer [wgslFunction]]
            ))

;; (js/console.log wgslFunction)
(def FONTS [{:family "Lato" :weight "black" :style "normal" :src "/Lato-Black.ttf"}])

(defn Camera [props]
  (let [children (.-children props)]
    #jsx [PanControls
          (fn [x y zoom]
            #jsx [FlatCamera {:x x :y y :zoom zoom} children])]))

(set! (.-displayName Camera) "Camera")

(defn App []
  (let [root (js/document.querySelector "#use-gpu")]
    #jsx [UseInspect {:provider DebugProvider :extensions [inspectGPU]}
          [WebGPU {:fallback (fn [error] #jsx [HTML {:container root} (makeFallback error)])}
           [AutoCanvas {:selector "#use-gpu .canvas" :samples 4}
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
                                    :image {:fit "scale"}}]])]]]]]]]]]]]))

(set! (.-displayName App) "App")

;; More robust initialization with error handling
(defn init []
  (if-let [root-element (js/document.querySelector "#use-gpu")]
    (do
      (js/console.log "Found #use-gpu element, rendering App")
      (render #jsx [App]))))

;; Make sure DOM is fully loaded
(if (= (.-readyState js/document) "loading")
  (js/document.addEventListener "DOMContentLoaded" init)
  (init))

;; Also try on window load as a fallback
(set! (.-onload js/window) init)
