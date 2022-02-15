package jpabook.jpashop.controller;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class MemberController {

    private final MemberService memberService;

    @GetMapping(value = "/members/new")
    public String createForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }

    @PostMapping(value = "/members/new")
    // name에 @NotEmpty를 설정해줬기 때문에 @Valid를 사용할 수 있다.
    // BindingResult bindingResult validation에서 error가 발생할 경우 오류가 bindingResult에 담긴 후 아래의 코드가 실행된다.
    // requestBody로 왜 Entity를 받지 않나요? -> Entity가 오염될 우려가 있고 실무에서 그 정도로 단순한 Entity를 사용하지 않는다.
    public String create(@Valid MemberForm memberForm, BindingResult bindingResult) {

        // error가 있다면 다시 members/createMemberForm로 보낸다.
        if (bindingResult.hasErrors()) {
            // error가 발생할 경우 request로 들어온 객체도 다시 return 해준다.
            return "members/createMemberForm";
        }

        Address address = new Address(memberForm.getCity(), memberForm.getStreet(), memberForm.getZipcode());

        Member member = new Member();
        member.setName(memberForm.getName());
        member.setAddress(address);

        //저장
        memberService.join(member);

        //저장 후 redirect
        return "redirect:/";
    }

    @GetMapping("/members")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }
}
