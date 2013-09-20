(ns js1k.common.data)

(defn rgb-color [x]
  (int (if (< 0 x 250) x (if (>= x 0) 250 (- x)))))

(defn deep-merge [& vals]
  (if (every? map? vals)
    (apply merge-with deep-merge vals)
    (last vals)))

(defn deep-merge2 [& maps]
  (apply merge-with deep-merge2 maps))

