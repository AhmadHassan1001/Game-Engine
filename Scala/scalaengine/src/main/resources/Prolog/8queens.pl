:-use_module(library(clpfd)).
n_queens(N, Qs) :-
        length(Qs, N),
        Qs ins 1..N,
        safe_queens(Qs).

safe_queens([]).
safe_queens([Q|Qs]) :-
        safe_queens(Qs, Q, 1),
        safe_queens(Qs).

safe_queens([], _, _).
safe_queens([Q|Qs], Q0, D0) :-
        Q0 #\= Q,
        abs(Q0 - Q) #\= D0,
        D1 #= D0 + 1,
        safe_queens(Qs, Q0, D1).

queens(Ls):-n_queens(8,Ls),labeling([], Ls).

test([_,_,1,8,_,_,_,_]).

% how to use
% test([_,_,1,8,_,_,_,_]).
% test(Ans),queens(Ans).