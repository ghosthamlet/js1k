(ns js1k.util
  (:use [seesaw.core]
        [clojure.algo.generic.math-functions]))

(def pi java.lang.Math/PI)

(defn usin [x] (sin x))
(defn ucos [x] (cos x))
(defn upow [x y] (pow x y))

(defprotocol PaintAll
  (paintall! [this bi]))

(extend-protocol PaintAll
  java.awt.Component
  (paintall! [this bi] (.paintAll this bi)))
;(paintall! c (.getGraphics bi))

(defn rgb-color [x]
  (if (< 0 x 250) x (if (>= x 0) 250 (- x))))

(defmacro defexample
  "Does the boilerplate for an example.

  arg-vec is a binding vector of arguments for the example, usually command-line
  args. body is code which must return an instance of javax.swing.JFrame. If the
  frame's size has not been set at all, pack! is called. Then show! is called.
  Defines two functions:
  run : takes an on-close keyword and trailing args and runs
  the example.
  -main : calls (run :exit & args). i.e. runs the example and exits when
  closed
  See the plethora of examples in this directory for usage examples.
  "
  [arg-vec & body]
  `(do
     (defn ~'run [on-close# & args#]
       (let [~arg-vec args#
             f# (invoke-now ~@body)]
         (config! f# :on-close on-close#)
          (when (= (java.awt.Dimension.) (.getSize f#)) 
            (pack! f#))
         (show! f#)))

     (defn ~'-main [& args#]
       (apply ~'run :exit args#))))
