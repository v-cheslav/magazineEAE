(function (a) {
    typeof define === "function" && define.amd && define.amd.jQuery ? define(["jquery"], a) : a(jQuery)
})(function (a) {
    function b(b) {
        if (b && b.allowPageScroll === void 0 && (b.swipe !== void 0 || b.swipeStatus !== void 0))b.allowPageScroll = i;
        if (b.click !== void 0 && b.tap === void 0)b.tap = b.click;
        b || (b = {});
        b = a.extend({}, a.fn.swipe.defaults, b);
        return this.each(function () {
            var g = a(this), d = g.data(R);
            d || (d = new c(this, b), g.data(R, d))
        })
    }

    function c(b, c) {
        function ha(b) {
            if (t.data(R + "_intouch") !== !0 && !(a(b.target).closest(c.excludedElements,
                    t).length > 0)) {
                var g = b.originalEvent ? b.originalEvent : b, d, k = r ? g.touches[0] : g;
                A = F;
                r ? B = g.touches.length : b.preventDefault();
                G = 0;
                K = H = null;
                N = O = D = 0;
                I = 1;
                L = 0;
                w = Ba();
                Y = Ca();
                Z = V = 0;
                if (!r || B === c.fingers || c.fingers === x || W()) {
                    if (ia(0, k), aa = P(), B == 2 && (ia(1, g.touches[1]), O = N = ja(w[0].start, w[1].start)), c.swipeStatus || c.pinchStatus)d = J(g, A)
                } else d = !1;
                if (d === !1)return A = z, J(g, A), d; else c.hold && (ka = setTimeout(a.proxy(function () {
                    t.trigger("hold", [g.target]);
                    c.hold && (d = c.hold.call(t, g, g.target))
                }, this), c.longTapThreshold)),
                    ba(!0);
                return null
            }
        }

        function la(b) {
            var o = b.originalEvent ? b.originalEvent : b;
            if (!(A === C || A === z || ta())) {
                var p, q = ua(r ? o.touches[0] : o);
                X = P();
                if (r)B = o.touches.length;
                c.hold && clearTimeout(ka);
                A = u;
                B == 2 && (O == 0 ? (ia(1, o.touches[1]), O = N = ja(w[0].start, w[1].start)) : (ua(o.touches[1]), N = ja(w[0].end, w[1].end), K = I < 1 ? j : h), I = (N / O * 1).toFixed(2), L = Math.abs(O - N));
                if (B === c.fingers || c.fingers === x || !r || W()) {
                    var n;
                    n = q.start;
                    var m = q.end;
                    n = Math.round(Math.atan2(m.y - n.y, n.x - m.x) * 180 / Math.PI);
                    n < 0 && (n = 360 - Math.abs(n));
                    n = H = n <= 45 &&
                    n >= 0 ? d : n <= 360 && n >= 315 ? d : n >= 135 && n <= 225 ? f : n > 45 && n < 135 ? k : g;
                    if (c.allowPageScroll === i || W())b.preventDefault(); else switch (m = c.allowPageScroll === l, n) {
                        case d:
                            (c.swipeLeft && m || !m && c.allowPageScroll != s) && b.preventDefault();
                            break;
                        case f:
                            (c.swipeRight && m || !m && c.allowPageScroll != s) && b.preventDefault();
                            break;
                        case g:
                            (c.swipeUp && m || !m && c.allowPageScroll != v) && b.preventDefault();
                            break;
                        case k:
                            (c.swipeDown && m || !m && c.allowPageScroll != v) && b.preventDefault()
                    }
                    G = Math.round(Math.sqrt(Math.pow(q.end.x - q.start.x, 2) + Math.pow(q.end.y -
                    q.start.y, 2)));
                    D = X - aa;
                    b = H;
                    n = G;
                    n = Math.max(n, va(b));
                    Y[b].distance = n;
                    if (c.swipeStatus || c.pinchStatus)p = J(o, A);
                    if (!c.triggerOnTouchEnd || c.triggerOnTouchLeave)b = !0, c.triggerOnTouchLeave && (b = a(this), n = b.offset(), b = {
                        left: n.left,
                        right: n.left + b.outerWidth(),
                        top: n.top,
                        bottom: n.top + b.outerHeight()
                    }, b = q.end.x > b.left && q.end.x < b.right && q.end.y > b.top && q.end.y < b.bottom), !c.triggerOnTouchEnd && b ? A = ma(u) : c.triggerOnTouchLeave && !b && (A = ma(C)), (A == z || A == C) && J(o, A)
                } else A = z, J(o, A);
                p === !1 && (A = z, J(o, A))
            }
        }

        function na(a) {
            var b =
                a.originalEvent;
            if (r && b.touches.length > 0)return V = P(), Z = event.touches.length + 1, !0;
            ta() && (B = Z);
            X = P();
            D = X - aa;
            oa() || !pa() ? (A = z, J(b, A)) : c.triggerOnTouchEnd || c.triggerOnTouchEnd == !1 && A === u ? (a.preventDefault(), A = C, J(b, A)) : !c.triggerOnTouchEnd && c.tap ? (A = C, S(b, A, q)) : A === u && (A = z, J(b, A));
            ba(!1);
            return null
        }

        function T() {
            N = O = aa = X = B = 0;
            I = 1;
            Z = V = 0;
            ba(!1)
        }

        function qa(a) {
            a = a.originalEvent;
            c.triggerOnTouchLeave && (A = ma(C), J(a, A))
        }

        function wa() {
            t.unbind(ca, ha);
            t.unbind(da, T);
            t.unbind(ra, la);
            t.unbind(sa, na);
            U && t.unbind(U,
                qa);
            ba(!1)
        }

        function ma(a) {
            var b = a, g = c.maxTimeThreshold ? D >= c.maxTimeThreshold ? !1 : !0 : !0, d = pa(), k = oa();
            !g || k ? b = z : d && a == u && (!c.triggerOnTouchEnd || c.triggerOnTouchLeave) ? b = C : !d && a == C && c.triggerOnTouchLeave && (b = z);
            return b
        }

        function J(a, b) {
            var g = void 0;
            if (xa() && ya() || ya())g = S(a, b, m); else if ((za() && W() || W()) && g !== !1)g = S(a, b, o);
            Aa() && c.doubleTap && g !== !1 ? g = S(a, b, p) : D > c.longTapThreshold && G < y && c.longTap && g !== !1 ? g = S(a, b, n) : (B === 1 || !r) && (isNaN(G) || G < c.threshold) && c.tap && g !== !1 && (g = S(a, b, q));
            b === z && T(a);
            b === C &&
            (r ? a.touches.length == 0 && T(a) : T(a));
            return g
        }

        function S(b, i, l) {
            var s = void 0;
            if (l == m) {
                t.trigger("swipeStatus", [i, H || null, G || 0, D || 0, B, w]);
                if (c.swipeStatus && (s = c.swipeStatus.call(t, b, i, H || null, G || 0, D || 0, B, w), s === !1))return !1;
                if (i == C && xa()) {
                    t.trigger("swipe", [H, G, D, B, w]);
                    if (c.swipe && (s = c.swipe.call(t, b, H, G, D, B, w), s === !1))return !1;
                    switch (H) {
                        case d:
                            t.trigger("swipeLeft", [H, G, D, B, w]);
                            c.swipeLeft && (s = c.swipeLeft.call(t, b, H, G, D, B, w));
                            break;
                        case f:
                            t.trigger("swipeRight", [H, G, D, B, w]);
                            c.swipeRight && (s = c.swipeRight.call(t,
                                b, H, G, D, B, w));
                            break;
                        case g:
                            t.trigger("swipeUp", [H, G, D, B, w]);
                            c.swipeUp && (s = c.swipeUp.call(t, b, H, G, D, B, w));
                            break;
                        case k:
                            t.trigger("swipeDown", [H, G, D, B, w]), c.swipeDown && (s = c.swipeDown.call(t, b, H, G, D, B, w))
                    }
                }
            }
            if (l == o) {
                t.trigger("pinchStatus", [i, K || null, L || 0, D || 0, B, I, w]);
                if (c.pinchStatus && (s = c.pinchStatus.call(t, b, i, K || null, L || 0, D || 0, B, I, w), s === !1))return !1;
                if (i == C && za())switch (K) {
                    case h:
                        t.trigger("pinchIn", [K || null, L || 0, D || 0, B, I, w]);
                        c.pinchIn && (s = c.pinchIn.call(t, b, K || null, L || 0, D || 0, B, I, w));
                        break;
                    case j:
                        t.trigger("pinchOut",
                            [K || null, L || 0, D || 0, B, I, w]), c.pinchOut && (s = c.pinchOut.call(t, b, K || null, L || 0, D || 0, B, I, w))
                }
            }
            if (l == q) {
                if (i === z || i === C)clearTimeout(ea), clearTimeout(ka), c.doubleTap && !Aa() ? (Q = P(), ea = setTimeout(a.proxy(function () {
                    Q = null;
                    t.trigger("tap", [b.target]);
                    c.tap && (s = c.tap.call(t, b, b.target))
                }, this), c.doubleTapThreshold)) : (Q = null, t.trigger("tap", [b.target]), c.tap && (s = c.tap.call(t, b, b.target)))
            } else if (l == p) {
                if (i === z || i === C)clearTimeout(ea), Q = null, t.trigger("doubletap", [b.target]), c.doubleTap && (s = c.doubleTap.call(t,
                    b, b.target))
            } else if (l == n && (i === z || i === C))clearTimeout(ea), Q = null, t.trigger("longtap", [b.target]), c.longTap && (s = c.longTap.call(t, b, b.target));
            return s
        }

        function pa() {
            var a = !0;
            c.threshold !== null && (a = G >= c.threshold);
            return a
        }

        function oa() {
            var a = !1;
            c.cancelThreshold !== null && H !== null && (a = va(H) - G >= c.cancelThreshold);
            return a
        }

        function za() {
            var a = B === c.fingers || c.fingers === x || !r, b = w[0].end.x !== 0, g;
            g = c.pinchThreshold !== null ? L >= c.pinchThreshold : !0;
            return a && b && g
        }

        function W() {
            return !(!c.pinchStatus && !c.pinchIn && !c.pinchOut)
        }

        function xa() {
            var a = c.maxTimeThreshold ? D >= c.maxTimeThreshold ? !1 : !0 : !0, b = pa(), g = B === c.fingers || c.fingers === x || !r, d = w[0].end.x !== 0;
            return !oa() && d && g && b && a
        }

        function ya() {
            return !(!c.swipe && !c.swipeStatus && !c.swipeLeft && !c.swipeRight && !c.swipeUp && !c.swipeDown)
        }

        function Aa() {
            if (Q == null)return !1;
            var a = P();
            return !!c.doubleTap && a - Q <= c.doubleTapThreshold
        }

        function ta() {
            var a = !1;
            V && P() - V <= c.fingerReleaseThreshold && (a = !0);
            return a
        }

        function ba(a) {
            a === !0 ? (t.bind(ra, la), t.bind(sa, na), U && t.bind(U, qa)) :
                (t.unbind(ra, la, !1), t.unbind(sa, na, !1), U && t.unbind(U, qa, !1));
            t.data(R + "_intouch", a === !0)
        }

        function ia(a, b) {
            w[a].identifier = b.identifier !== void 0 ? b.identifier : 0;
            w[a].start.x = w[a].end.x = b.pageX || b.clientX;
            w[a].start.y = w[a].end.y = b.pageY || b.clientY;
            return w[a]
        }

        function ua(a) {
            var b;
            a:{
                for (b = 0; b < w.length; b++)if (w[b].identifier == (a.identifier !== void 0 ? a.identifier : 0)) {
                    b = w[b];
                    break a
                }
                b = void 0
            }
            b.end.x = a.pageX || a.clientX;
            b.end.y = a.pageY || a.clientY;
            return b
        }

        function Ba() {
            for (var a = [], b = 0; b <= 5; b++)a.push({
                start: {
                    x: 0,
                    y: 0
                }, end: {x: 0, y: 0}, identifier: 0
            });
            return a
        }

        function va(a) {
            if (Y[a])return Y[a].distance
        }

        function Ca() {
            var a = {};
            a[d] = fa(d);
            a[f] = fa(f);
            a[g] = fa(g);
            a[k] = fa(k);
            return a
        }

        function fa(a) {
            return {direction: a, distance: 0}
        }

        function ja(a, b) {
            var c = Math.abs(a.x - b.x), g = Math.abs(a.y - b.y);
            return Math.round(Math.sqrt(c * c + g * g))
        }

        function P() {
            return (new Date).getTime()
        }

        var ga = r || M || !c.fallbackToMouseEvents, ca = ga ? M ? E ? "MSPointerDown" : "pointerdown" : "touchstart" : "mousedown", ra = ga ? M ? E ? "MSPointerMove" : "pointermove" : "touchmove" :
            "mousemove", sa = ga ? M ? E ? "MSPointerUp" : "pointerup" : "touchend" : "mouseup", U = ga ? null : "mouseleave", da = M ? E ? "MSPointerCancel" : "pointercancel" : "touchcancel", G = 0, H = null, D = 0, O = 0, N = 0, I = 1, L = 0, K = 0, Y = null, t = a(b), A = "start", B = 0, w = null, aa = 0, X = 0, V = 0, Z = 0, Q = 0, ea = null, ka = null;
        try {
            t.bind(ca, ha), t.bind(da, T)
        } catch (Da) {
            a.error("events not supported " + ca + "," + da + " on jQuery.swipe")
        }
        this.enable = function () {
            t.bind(ca, ha);
            t.bind(da, T);
            return t
        };
        this.disable = function () {
            wa();
            return t
        };
        this.destroy = function () {
            wa();
            t.data(R, null);
            return t
        };
        this.option = function (b, g) {
            if (c[b] !== void 0)if (g === void 0)return c[b]; else c[b] = g; else a.error("Option " + b + " does not exist on jQuery.swipe.options");
            return null
        }
    }

    var d = "left", f = "right", g = "up", k = "down", h = "in", j = "out", i = "none", l = "auto", m = "swipe", o = "pinch", q = "tap", p = "doubletap", n = "longtap", s = "horizontal", v = "vertical", x = "all", y = 10, F = "start", u = "move", C = "end", z = "cancel", r = "ontouchstart"in window, E = window.navigator.msPointerEnabled && !window.navigator.pointerEnabled, M = window.navigator.pointerEnabled || window.navigator.msPointerEnabled,
        R = "TouchSwipe";
    a.fn.swipe = function (c) {
        var g = a(this), d = g.data(R);
        if (d && typeof c === "string")if (d[c])return d[c].apply(this, Array.prototype.slice.call(arguments, 1)); else a.error("Method " + c + " does not exist on jQuery.swipe"); else if (!d && (typeof c === "object" || !c))return b.apply(this, arguments);
        return g
    };
    a.fn.swipe.defaults = {
        fingers: 1,
        threshold: 75,
        cancelThreshold: null,
        pinchThreshold: 20,
        maxTimeThreshold: null,
        fingerReleaseThreshold: 250,
        longTapThreshold: 500,
        doubleTapThreshold: 200,
        swipe: null,
        swipeLeft: null,
        swipeRight: null,
        swipeUp: null,
        swipeDown: null,
        swipeStatus: null,
        pinchIn: null,
        pinchOut: null,
        pinchStatus: null,
        click: null,
        tap: null,
        doubleTap: null,
        longTap: null,
        hold: null,
        triggerOnTouchEnd: !0,
        triggerOnTouchLeave: !1,
        allowPageScroll: "auto",
        fallbackToMouseEvents: !0,
        excludedElements: "label, button, input, select, textarea, a, .noSwipe"
    };
    a.fn.swipe.phases = {PHASE_START: F, PHASE_MOVE: u, PHASE_END: C, PHASE_CANCEL: z};
    a.fn.swipe.directions = {LEFT: d, RIGHT: f, UP: g, DOWN: k, IN: h, OUT: j};
    a.fn.swipe.pageScroll = {
        NONE: i, HORIZONTAL: s,
        VERTICAL: v, AUTO: l
    };
    a.fn.swipe.fingers = {ONE: 1, TWO: 2, THREE: 3, ALL: x}
});
;
(function () {
    if (!("undefined" == typeof Muse || "undefined" == typeof Muse.assets)) {
        var a = function (a, b) {
            for (var c = 0, d = a.length; c < d; c++)if (a[c] == b)return c;
            return -1
        }(Muse.assets.required, "touchswipe.js");
        if (-1 != a) {
            Muse.assets.required.splice(a, 1);
            for (var a = document.getElementsByTagName("meta"), b = 0, c = a.length; b < c; b++) {
                var d = a[b];
                if ("generator" == d.getAttribute("name")) {
                    "2015.0.2.310" != d.getAttribute("content") && Muse.assets.outOfDate.push("touchswipe.js");
                    break
                }
            }
        }
    }
})();
