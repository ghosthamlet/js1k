(ns js1k.j2012.spring.conch
  (:use [seesaw.core] 
        [seesaw.graphics]
        [seesaw.color]
        [js1k.util :only [defexample]]
        [js1k.j2012.spring.model.conch]
        :reload)
  (:import java.awt.Font))

(defn conch [c g]
  (swap! m #(+ @n %))
  (if (or (> @m 254) (< @m 1)) (swap! n #(- %)))
  (dotimes [i -m]
    (draw g
          (rect (valid-coor (:s-x (get @p i))) (valid-coor (:s-y (get @p i))) ds ds)
          (style :background (color @m (/ i 4) 128)))))

(defn paint [c g]
  (draw g
        (rect 0 0 w h)
        (style :background (color 0 0 0 255)))
  (push g
        (conch c g))
  (re-calc))

(defn add-behaviors [root]
  "http://js1k.com/2010-first/demo/666"
  (let [c (select root [:#canvas])
        tt (timer (fn [_] (repaint! c))
                  :delay 10
                  :start? true)]
    (config! c :paint #(paint %1 %2))
    ; Clean up timer on close (useful in repl)
    (listen root :window-closing (fn [_] (.stop tt)))
    root))

