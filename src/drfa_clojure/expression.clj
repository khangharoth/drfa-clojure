(ns drfa-clojure.expression
  [:require [clojure.math.numeric-tower :as math]])

(defn int-tolerance-expression [tolerance]
  (fn [base-value target-value]
    (let [base-int (Integer. base-value) target-int (Integer. target-value)]
      (> tolerance (math/abs (- base-int target-int))))))

(defn parse-date [format value]
  (.parse (java.text.SimpleDateFormat. format) value))

(defn date-expression [base-format target-format]
  (fn [base-value target-value]
    (let [base-date (parse-date base-format base-value) target-date (parse-date target-format target-value)]
      (.equals base-date target-date))))
