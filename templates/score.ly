#(set-global-staff-size 22)

\header{
	tagline = "" 
	title = "Reflections"
	composer = "Brian Ellis"
	subtitle="#
%timeStamp
"
}


\markup{\column {
      \line { "   " }
      \line { This piece is to be played very freely in ever regard. }
      \line { Note duration, phrasing, accents, timber, and general }
      \line { musicianship should all be varied throughout the piece to }
      \line { create a highly dynamic performance. }
      \line { "   " }
      \line { For variations of this piece, please visit: }
      \line { www.BrianEllisMusic.com/reflections }
    }
}

\paper{
  indent = 2\cm
  left-margin = 1.5\cm
  right-margin = 1.5\cm
  top-margin = 2\cm
  bottom-margin = 1.5\cm
  ragged-last-bottom = ##t
  print-all-headers = ##t
  print-page-number = ##f
  ragged-last-bottom = ##f
}

\score{
\header{
	tagline = "" 
	title = "  "
	subtitle="  "
	composer = "  "
}
 \new  StaffGroup  <<
%part0
%part1
%part2
%part3
%part4
%part5
%part6
%part7
%part8
%part9
>>
\layout{}
\midi{}
}